package js.hera.hub.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Set;

import js.hera.DeviceDiscoveryMessage;
import js.hera.HostAdvertise;
import js.log.Log;
import js.log.LogFactory;
import js.util.Files;

/**
 * Base implementation for discovery and advertise protocols. Discovery message is broadcasted by this handler under
 * {@link DeviceManager} control while advertise message is generated spontaneously by device instance, on its boot up.
 * <p>
 * There are specialized discovery handler implementations for all supported protocols, see {@link ProtocolStack}.
 * 
 * @author Iulian Rotaru
 */
public abstract class DiscoveryHandler implements Runnable
{
  /** Class logger. */
  private static final Log log = LogFactory.getLog(DiscoveryHandler.class);

  /** Maximum advertise datagram size. */
  protected static final int MAX_ADVERTISE_SIZE = 1000;

  /** Shutdown command used to interrupt listening socket. */
  private static final String SHUTDOWN = "SHUTDOWN";

  /** Thread stop timeout. */
  private static final int STOP_TIMEOUT = 2000;

  protected final Set<InetAddress> broadcastAddresses;
  private int port;

  /**
   * Device advertise listener is notified when a new advertise message is received. Discovery handler supports only one
   * listener.
   */
  private HostAdvertiseListener listener;

  private Thread thread;

  protected DatagramSocket socket;

  protected DiscoveryHandler(int port)
  {
    log.trace("DiscoveryHandler(int)");
    this.broadcastAddresses = NetInterfaces.getInstance().getBroadcastAddresses();
    this.port = port;
  }

  /**
   * Set device advertise listener.
   * 
   * @param listener device advertise listener.
   * @see #listener
   */
  public void setListener(HostAdvertiseListener listener)
  {
    this.listener = listener;
  }

  public void start()
  {
    log.trace("start()");
    thread = new Thread(this, "discovery");
    thread.start();
  }

  public void stop()
  {
    log.trace("stop()");
    if(!thread.isAlive()) {
      log.error("Attempt to stop already stopped thread |%s|.", thread.getName());
      return;
    }

    DatagramSocket shutdownSocket = null;
    try {
      shutdownSocket = new DatagramSocket();
      byte[] payload = SHUTDOWN.getBytes();
      DatagramPacket packet = new DatagramPacket(payload, payload.length, InetAddress.getLocalHost(), port);
      shutdownSocket.send(packet);
    }
    catch(IOException e) {
      log.error("Fail to send SHUTDOWN packet to discovery thread |%s|. Cause: %s", Thread.currentThread().getName(), e.getMessage());
      thread.interrupt();
    }
    finally {
      Files.close(shutdownSocket);
    }

    try {
      thread.join(STOP_TIMEOUT);
    }
    catch(InterruptedException e) {
      log.bug("Close timeout on discovery thread |%s|. Ignore it.", Thread.currentThread().getName());
    }
  }

  @Override
  public void run()
  {
    log.debug("Start discovery handler thread |%s|.", thread.getName());

    try {
      socket = new DatagramSocket(null);
      socket.bind(new InetSocketAddress(port));
      socket.setBroadcast(true);
    }
    catch(SocketException e) {
      log.error("Fail to create socket on port |%d|. Abort discovery handler thread |%s|. Error cause: %s.", port, thread.getName(), e.getMessage());
      Files.close(socket);
      return;
    }
    log.debug("Open socket on port |%d|. Waiting for discovery messages.", port);

    byte[] buffer = new byte[MAX_ADVERTISE_SIZE];
    DatagramPacket advertisePacket = new DatagramPacket(buffer, buffer.length);

    for(;;) {
      try {
        // wait for device descriptor message; expected to contain UTF-8 characters
        socket.receive(advertisePacket);
        String message = new String(buffer, 0, advertisePacket.getLength(), Charset.forName("UTF-8"));
        log.debug(message);

        if(message.equals(SHUTDOWN)) {
          if(!advertisePacket.getAddress().equals(InetAddress.getLocalHost())) {
            // to test this condition uses netcat command from a remote linux box
            // echo -n "SHUTDOWN" | nc -4u -w1 192.168.1.3 1901
            log.warn("Security alert. Receive shutdown command from foreign host |%s|. Ignore it.", advertisePacket.getAddress());
            continue;
          }
          log.debug("Shutdown command received. Stop discovery handler thread |%s|.", thread.getName());
          break;
        }

        if(!advertisePacket.getAddress().isSiteLocalAddress()) {
          log.warn("Security alert. Receive device advertise packet from foreign network |%s|. Ignore it.", advertisePacket.getAddress());
          continue;
        }

        HostAdvertise advertise = parseDeviceAdvertise(message);
        if(advertise != null) {
          listener.onDeviceAdvertise(advertise);
        }
      }
      catch(IOException e) {
        log.error("IO exception when trying to read discovery message: %s", e);
      }
      catch(Exception e) {
        log.error("Unexpected exception while processing discovery message: %s", e);
      }
    }

    Files.close(socket);
    log.debug("Discovery handler thread |%s| finished.", thread.getName());
  }

  public abstract void broadcastDiscoveryMessage(DeviceDiscoveryMessage deviceDiscoveryMessage) throws IOException;

  /**
   * Return device advertise instance or null if given message does not contain one.
   * 
   * @param message
   * @return
   * @throws IOException
   */
  protected abstract HostAdvertise parseDeviceAdvertise(String message) throws IOException;
}
