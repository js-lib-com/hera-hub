package js.hera.hub.model;

import java.util.ArrayList;
import java.util.List;

public class SystemDescriptor
{
  private List<Zone> zones;
  private List<DeviceCategory> deviceCategories;
  private List<DeviceDescriptor> deviceDescriptors;
  private List<String> iconNames;

  public List<Zone> getZones()
  {
    return zones;
  }

  public void setZones(List<Zone> zones)
  {
    this.zones = zones;
  }

  public List<DeviceCategory> getDeviceCategories()
  {
    return deviceCategories;
  }

  public void setDeviceCategories(List<DeviceCategory> deviceCategories)
  {
    this.deviceCategories = deviceCategories;
  }

  public List<DeviceDescriptor> getDeviceDescriptors()
  {
    return deviceDescriptors;
  }

  public void setDeviceDescriptors(List<DeviceDescriptor> deviceDescriptors)
  {
    this.deviceDescriptors = deviceDescriptors;
  }

  public List<DeviceDescriptor> getDevicesByZone(int zoneId)
  {
    List<DeviceDescriptor> devices = new ArrayList<DeviceDescriptor>();
    for(DeviceDescriptor device : deviceDescriptors) {
      if(device.getZoneId() == zoneId) {
        devices.add(device);
      }
    }
    return devices;
  }

  public List<DeviceDescriptor> getDevicesByCategory(int categoryId)
  {
    List<DeviceDescriptor> devices = new ArrayList<DeviceDescriptor>();
    for(DeviceDescriptor device : deviceDescriptors) {
      if(device.getCategoryId() == categoryId) {
        devices.add(device);
      }
    }
    return devices;
  }

  public List<String> getIconNames()
  {
    return iconNames;
  }

  public void setIconNames(List<String> iconNames)
  {
    this.iconNames = iconNames;
  }
}
