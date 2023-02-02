package js.hera.hub.client;

import java.util.List;
import java.util.function.Consumer;

import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.SystemDescriptor;
import js.hera.hub.model.Zone;

public interface Service
{
  // ------------------------------------------------------
  // HERA Smart Hub services

  void getSystemDescriptor(Consumer<SystemDescriptor> consumer);

  void invokeDeviceAction(String deviceName, String actionName, Consumer<?> result, Object... arguments);

  /**
   * Convenient way to invoker void method without providing consumer for asynchronous mode.
   * 
   * @param deviceName
   * @param actionName
   * @param arguments
   */
  void invokeDeviceAction(String deviceName, String actionName, Object... arguments);

  // ------------------------------------------------------
  // HERA Admin Console services

  void getDevicesByZone(int zoneId, Consumer<List<DeviceDescriptor>> consumer);

  void getDevicesByCategory(int categoryId, Consumer<List<DeviceDescriptor>> consumer);

  void createZone(Zone zone);

  void readZone(int zoneId, Consumer<Zone> consumer);

  void updateZone(Zone zone);

  void deleteZone(int zoneId);

  void createDevice(DeviceDescriptor device);

  void readDevice(int deviceId, Consumer<DeviceDescriptor> consumer);

  void updateDevice(DeviceDescriptor device);

  void deleteDevice(int deviceId);
}