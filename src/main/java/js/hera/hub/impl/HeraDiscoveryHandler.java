package js.hera.hub.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;

import js.hera.DeviceDiscoveryMessage;
import js.hera.HostAdvertise;
import js.json.Json;
import js.json.ex.JsonEx;
import js.log.Log;
import js.log.LogFactory;

/**
 * Discovery protocol for HERA genuine devices.
 * 
 * @author Iulian Rotaru
 */
public class HeraDiscoveryHandler extends DiscoveryHandler
{
  private static final Log log = LogFactory.getLog(HeraDiscoveryHandler.class);

  /** HERA discovery port. */
  private static final int DISCOVERY_PORT = 1902;

  private final Json json;

  public HeraDiscoveryHandler()
  {
    super(DISCOVERY_PORT);
    json = new JsonEx();
  }

  @Override
  public void broadcastDiscoveryMessage(DeviceDiscoveryMessage deviceDiscoveryMessage) throws IOException
  {
    log.trace("broadcastDiscoveryMessage(DiscoveryMessage)");

    StringWriter writer = new StringWriter();
    json.stringify(writer, deviceDiscoveryMessage);
    byte requestPayload[] = writer.toString().getBytes();

    for(InetAddress broadcastAddress : broadcastAddresses) {
      log.debug("Broadcast discovery message to |%s|.", broadcastAddress);
      DatagramPacket requestPacket = new DatagramPacket(requestPayload, requestPayload.length, broadcastAddress, DISCOVERY_PORT);
      socket.send(requestPacket);
    }
  }

  @Override
  protected HostAdvertise parseDeviceAdvertise(String message) throws IOException
  {
    log.trace("parseDeviceAdvertise(String)");

    StringReader reader = new StringReader(message);
    Object object = json.parse(reader, Object.class);

    if(!(object instanceof HostAdvertise)) {
      return null;
    }
    return (HostAdvertise)object;
  }
}
