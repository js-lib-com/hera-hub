package js.hera;

import java.net.URL;

import js.hera.dev.Device;

/**
 * 
 * Advertise message send by a device instances, deployed on a target host. All instances described by this descriptor
 * have the same class and are deployed on the same target host. Every instance has a name that should be unique on HERA
 * space. Is HERA deployer responsibility to ensure device instance name uniqueness.
 * <p>
 * Target host advertise this descriptor on its initialization or as response to controller search operation.
 * 
 * @author Iulian Rotaru
 */
@Deprecated
public class DeviceAdvertiseMessage
{
  /**
   * Advertise time stamp. All advertise messages from an advertise session have the same time stamp, that is, the unix
   * time of the first message. This field is used by controllers to avoid multiple processing.
   */
  private long timestamp;

  private Class<? extends Device> deviceClass;
  /** The URL of the host system where device instances are deployed. */
  private URL hostURL;
  /** Name of device instances, of type specified by <code>deviceClass</code> field. */
  private String[] instances;

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

  public URL getHostURL()
  {
    return hostURL;
  }

  public void setHostURL(URL hostURL)
  {
    this.hostURL = hostURL;
  }

  public String[] getInstances()
  {
    return instances;
  }

  public void setInstances(String[] instances)
  {
    this.instances = instances;
  }
}
