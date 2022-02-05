package js.hera.hub.model;

import js.hera.dev.Device;
import js.hera.hub.dao.Dao;

@SuppressWarnings("unused")
public class DeviceDTO
{
  private int id;
  private int zoneId;
  private int categoryId;
  private int hostId;
  private String name;
  private String display;
  private Class<? extends Device> deviceClass;

  private String zoneDisplay;
  private String categoryDisplay;
  private String hostDisplay;

  public DeviceDTO(Dao dao, DeviceDescriptor descriptor)
  {
    this.id = descriptor.getId();
    this.zoneId = descriptor.getZoneId();
    this.categoryId = descriptor.getCategoryId();
    this.hostId = descriptor.getHostId();
    this.name = descriptor.getName();
    this.display = descriptor.getDisplay();
    this.deviceClass = descriptor.getDeviceClass();

    Zone zone = dao.getZone(descriptor.getZoneId());
    if(zone != null) {
      zoneDisplay = zone.getDisplay();
    }
    DeviceCategory category = dao.getCategory(descriptor.getCategoryId());
    if(category != null) {
      categoryDisplay = category.getDisplay();
    }
    Host host = dao.getHost(descriptor.getHostId());
    if(host != null) {
      hostDisplay = host.getDisplay();
    }
  }
}
