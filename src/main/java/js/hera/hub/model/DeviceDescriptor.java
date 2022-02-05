package js.hera.hub.model;

import js.hera.dev.Device;
import js.hera.hub.dao.Dao;
import js.hera.hub.dao.PostLoad;

public class DeviceDescriptor implements PostLoad
{
  /** ID used internally by persistence logic. */
  private int id;

  private int zoneId;
  private int categoryId;
  private int hostId;

  /** Device unique name, used to identify device instance into HERA scope. */
  private String name;

  private String display;

  /** Device class describe actions supported by a certain device instance. */
  private Class<? extends Device> deviceClass;

  private transient String hostname;
  private transient String hostDisplay;

  public void update(DeviceDescriptor device)
  {
    this.zoneId = device.zoneId;
    this.categoryId = device.categoryId;
    this.hostId = device.hostId;
    this.name = device.name;
    this.display = device.display;
    this.deviceClass = device.deviceClass;
  }

  @Override
  public void postLoad(Dao dao)
  {
    Host host = dao.getHost(hostId);
    if(host != null) {
      hostname = host.getName();
      hostDisplay = host.getDisplay();
    }
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

  public int getHostId()
  {
    return hostId;
  }

  public String getHostname()
  {
    return hostname;
  }

  public String getHostDisplay()
  {
    return hostDisplay;
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

  @Override
  public String toString()
  {
    return name;
  }
}
