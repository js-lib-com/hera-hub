package js.hera.hub.dao;

import java.util.List;

import js.hera.hub.model.DeviceCategory;
import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.Host;
import js.hera.hub.model.User;
import js.hera.hub.model.Zone;

/**
 * Data access object.
 * 
 * @author Iulian Rotaru
 */
public interface Dao
{
  User getUserById(int userId);

  List<Zone> getZones();

  List<DeviceCategory> getDeviceCategories();

  List<DeviceDescriptor> getDeviceDescriptors();

  List<DeviceDescriptor> getDevicesByZone(int zoneId);

  int getDevicesCountByZone(int zoneId);

  List<DeviceDescriptor> getDevicesByCategory(int categoryId);

  int getDevicesCountByCategory(int categoryId);

  DeviceDescriptor getDeviceDescriptor(String deviceName);

  void createZone(Zone zone);

  Zone readZone(int zoneId);

  void updateZone(Zone zone);

  void deleteZone(int zoneId);

  void createDevice(DeviceDescriptor device);

  DeviceDescriptor readDevice(int deviceId);

  void updateDevice(DeviceDescriptor device);

  void deleteDevice(int deviceId);

  DeviceCategory getDeviceCategoryByName(String categoryName);

  void deleteCategory(int categoryId);

  Zone getZone(int zoneId);

  DeviceCategory getCategory(int categoryId);

  void createCategory(DeviceCategory category);

  void updateCategory(DeviceCategory category);

  DeviceDescriptor getDevice(int deviceId);

  List<Host> getHosts();

  Host getHostByName(String hostName);

  void updateHost(Host host);

  void clearHosts();

  List<Zone> getZonesByIcon(String iconName);

  List<DeviceCategory> getCategoriesByIcon(String iconName);
}
