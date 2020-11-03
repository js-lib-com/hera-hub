package js.hera.hub.model;

import java.net.URL;

import js.hera.dev.Device;

public class DeviceDescriptor
{
  /** ID used internally by persistence logic. */
  private int id;

  private int zoneId;
  private int categoryId;

  /** Device unique name, used to identify device instance into HERA scope. */
  private String name;

  private String display;

  /** Device class describe actions supported by a certain device instance. */
  private Class<? extends Device> deviceClass;

  /** URL of the target host context where device instance is deployed. */
  private transient URL hostURL;

  public void update(DeviceDescriptor device)
  {
    this.zoneId = device.zoneId;
    this.categoryId = device.categoryId;
    this.name = device.name;
    this.display = device.display;
    this.deviceClass = device.deviceClass;
  }

  public int getId()
  {
    return id;
  }

  public int getZoneId()
  {
    return zoneId;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDisplay()
  {
    return display;
  }

  public void setDisplay(String display)
  {
    this.display = display;
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

  public boolean isDeviceActive()
  {
    // uses host system URL to detect if device is active
    return hostURL != null;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
