package js.hera.hub.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.util.Params;

/**
 * Network interfaces attached to host system. This class implements logic to retrieve local IP address able to send
 * discovery response, given IP address of the host that requested for discovery. Takes care to serve only hosts from
 * local site, returning null for discovery requests from foreign networks.
 * 
 * @author Iulian Rotaru
 */
public final class NetInterfaces
{
  /** Class logger. */
  private static final Log log = LogFactory.getLog(NetInterfaces.class);

  /** Network interfaces list. Does not contain local loopback. */
  private final List<NetInterfaces.NetInterface> netInterfaces = new ArrayList<NetInterfaces.NetInterface>();

  private final Set<InetAddress> broadcastAddresses = new HashSet<>();

  /**
   * Scan host system interfaces and initialize internal network interfaces list. See {@link NetInterface} for relevant
   * structure.
   */
  public NetInterfaces()
  {
    log.trace("NetInterfaces()");
    scanNetInterfaces();
  }

  /**
   * Set requested bit value on the given bytes array. Bit index is relative to entire bytes array and should be in
   * range [0, 8 * bytes.length).
   * 
   * @param bytes bytes array,
   * @param index bit index, on entire bytes array.
   */
  private static void setBit(byte[] bytes, int index)
  {
    Params.LT(index, 8 * bytes.length, "Bit index");
    bytes[index / 8] |= (1 << (index % 8));
  }

  /**
   * Clear requested bit value on the given bytes array. Bit index is relative to entire bytes array and should be in
   * range [0, 8 * bytes.length).
   * 
   * @param bytes bytes array,
   * @param index bit index, on entire bytes array.
   */
  private static void clearBit(byte[] bytes, int index)
  {
    Params.LT(index, 8 * bytes.length, "Bit index");
    bytes[index / 8] &= ~(1 << (index % 8));
  }

  public String getInterfaceAddress(String hostName)
  {
    try {
      return getInterfaceAddress(InetAddress.getByName(hostName));
    }
    catch(UnknownHostException e) {
      log.warn("Unknown host for remote host |%s|.", hostName);
    }
    return null;
  }

  /**
   * Get IP address of the network interface able to route datagram to given remote IP address. Return null if requested
   * <code>ipAddress</code> is not on local site.
   * 
   * @param hostAddress IP address on remote host.
   * @return local IP address or null.
   */
  public String getInterfaceAddress(InetAddress hostAddress)
  {
    int bytesIndex = 0;
    int tries = 2;

    while(tries-- > 0) {
      for(NetInterfaces.NetInterface netInterface : netInterfaces) {
        byte[] ipBytes = hostAddress.getAddress();
        assert ipBytes.length == netInterface.bytesCount;

        for(bytesIndex = 0;; ++bytesIndex) {
          if(bytesIndex == netInterface.bytesCount) {
            return netInterface.ipAddress;
          }
          if(netInterface.netBytes[bytesIndex] != (ipBytes[bytesIndex] & netInterface.netMask[bytesIndex])) {
            break;
          }
        }
      }

      log.debug("No network interface found for IP address |%s|. Rescan host network interfaces.", hostAddress);
      scanNetInterfaces();
    }

    log.debug("No network interface found for IP address |%s|.", hostAddress);
    return null;
  }

  public Set<InetAddress> getBroadcastAddresses()
  {
    return broadcastAddresses;
  }

  private void scanNetInterfaces()
  {
    netInterfaces.clear();
    broadcastAddresses.clear();

    try {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
      while(networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        if(networkInterface.isLoopback()) {
          continue;
        }
        for(InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
          // for now only IPv4
          if(!(interfaceAddress.getAddress() instanceof Inet4Address)) {
            continue;
          }
          NetInterfaces.NetInterface netInterface = new NetInterface();
          netInterfaces.add(netInterface);

          netInterface.netBytes = interfaceAddress.getAddress().getAddress();
          netInterface.bytesCount = netInterface.netBytes.length;
          netInterface.netMask = new byte[netInterface.bytesCount];
          netInterface.ipAddress = interfaceAddress.getAddress().getHostAddress();
          broadcastAddresses.add(interfaceAddress.getBroadcast());

          for(int i = 0; i < 32; ++i) {
            if(i < interfaceAddress.getNetworkPrefixLength()) {
              setBit(netInterface.netMask, i);
            }
            else {
              clearBit(netInterface.netBytes, i);
            }
          }
        }
      }
    }
    catch(SocketException e) {
      // no apparent reason to have IO exception while reading socket structure from kernel
      log.error(e);
    }

    StringBuilder dump = new StringBuilder();
    dump.append("Discovered host network interfaces:");
    for(NetInterfaces.NetInterface netInterface : netInterfaces) {
      dump.append("\r\n\t- ");
      dump.append(netInterface.ipAddress);
    }
    log.debug(dump.toString());
  }

  /**
   * Network interface structure.
   * 
   * @author Iulian Rotaru
   */
  private static class NetInterface
  {
    /**
     * Network address bytes. Contains only network relevant bits accordingly network address mask. Host bits are
     * cleared to zero.
     */
    byte[] netBytes;

    /** Network address mask. */
    byte[] netMask;

    /** Network address bytes count. For now only IPv4 therefore constant value 4 bytes. */
    int bytesCount;

    /** Full IP address, that is, both network and host part. */
    String ipAddress;
  }
}