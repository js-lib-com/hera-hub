package js.hera.hub.model;

import js.hera.dev.Device;
import js.hera.hub.dao.Dao;
import js.tiny.container.core.Factory;

@SuppressWarnings("unused")
public class DeviceDTO
{
  private int id;
  private int zoneId;
  private int categoryId;
  private String name;
  private String display;
  private Class<? extends Device> deviceClass;

  private String zoneDisplay;
  private String categoryDisplay;

  public DeviceDTO(DeviceDescriptor descriptor)
  {
    this.id = descriptor.getId();
    this.zoneId = descriptor.getZoneId();
    this.categoryId = descriptor.getCategoryId();
    this.name = descriptor.getName();
    this.display = descriptor.getDisplay();
    this.deviceClass = descriptor.getDeviceClass();

    Dao dao = Factory.getInstance(Dao.class);
    Zone zone = dao.getZone(descriptor.getZoneId());
    if(zone != null) {
      zoneDisplay = zone.getDisplay();
    }
    DeviceCategory category = dao.getCategory(descriptor.getCategoryId());
    if(category != null) {
      categoryDisplay = category.getDisplay();
    }
  }
}
