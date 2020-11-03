package js.hera;

import js.hera.dev.Device;
import js.hera.hub.impl.HeraDiscoveryHandler;

/**
 * Discovery message broadcasted by control points to detect device instances location. This is a generic message, used
 * as it is only by {@link HeraDiscoveryHandler}. For not genuine HERA devices this message is translated accordingly
 * protocol stack specs.
 * 
 * @author Iulian Rotaru
 */
public class DeviceDiscoveryMessage
{
  /**
   * Discovery message time stamp. All discovery messages from a discovery session have the same time stamp, that is,
   * the unix time of the first message. This field is used by target host to avoid multiple processing.
   */
  private long timestamp;

  /** Device class to search for. */
  private Class<? extends Device> deviceClass;

  /**
   * Search for specific device instance name. This is not a so much a search but more a state query since is targeted
   * to a single, specific device instance.
   */
  private String deviceName;

  /**
   * Delay spread, in seconds, used by device to randomly wait before responding to search. Device delay is in range [0,
   * delaySpread]. Device uses this value as a hint.
   */
  private int delaySpread;

  public long getTimestamp()
  {
    return timestamp;
  }

  public void setTimestamp(long timestamp)
  {
    this.timestamp = timestamp;
  }

  public Class<? extends Device> getDeviceClass()
  {
    return deviceClass;
  }

  public void setDeviceClass(Class<? extends Device> deviceClass)
  {
    this.deviceClass = deviceClass;
  }

  public String getDeviceName()
  {
    return deviceName;
  }

  public void setDeviceName(String deviceName)
  {
    this.deviceName = deviceName;
  }

  public int getDelaySpread()
  {
    return delaySpread;
  }

  public void setDelaySpread(int delaySpread)
  {
    this.delaySpread = delaySpread;
  }
}
