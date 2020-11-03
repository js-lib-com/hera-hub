package js.hera.hub.model;

import java.net.URL;

import js.hera.dev.Device;

/**
 * Meta-data about device instance.
 * 
 * @author Iulian Rotaru
 */
public class DeviceInstance
{
  /**
   * Device instance name, unique on HERA network. Is legal to have multiple device instances of the same class but
   * every should have its own name. Be aware that this name identifies device running instance not device class.
   */
  private String deviceName;

  /** Device class describe actions supported by a certain device instance. */
  private Class<? extends Device> deviceClass;

  /** URL of the target host context where device instance is deployed. */
  private URL deviceURL;

  public void setDeviceClass(Class<? extends Device> deviceClass)
  {
    this.deviceClass = deviceClass;
  }

  public Class<? extends Device> getDeviceClass()
  {
    return deviceClass;
  }

  public void setDeviceURL(URL deviceURL)
  {
    this.deviceURL = deviceURL;
  }

  public URL getDeviceURL()
  {
    return deviceURL;
  }

  public void setDeviceName(String deviceName)
  {
    this.deviceName = deviceName;
  }

  public String getDeviceName()
  {
    return deviceName;
  }
}
