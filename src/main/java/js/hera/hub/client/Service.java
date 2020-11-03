package js.hera.hub.client;

import java.util.List;

import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.SystemDescriptor;
import js.hera.hub.model.Zone;
import js.lang.Callback;

public interface Service
{
  // ------------------------------------------------------
  // HERA Smart Hub services

  void getSystemDescriptor(Callback<SystemDescriptor> callback);

  void invokeDeviceAction(String deviceName, String actionName, Callback<?> result, Object... arguments);

  /**
   * Convenient way to invoker void method without providing callback for asynchronous mode.
   * 
   * @param deviceName
   * @param actionName
   * @param arguments
   */
  void invokeDeviceAction(String deviceName, String actionName, Object... arguments);

  // ------------------------------------------------------
  // HERA Admin Console services

  void getDevicesByZone(int zoneId, Callback<List<DeviceDescriptor>> callback);

  void getDevicesByCategory(int categoryId, Callback<List<DeviceDescriptor>> callback);

  void createZone(Zone zone);

  void readZone(int zoneId, Callback<Zone> callback);

  void updateZone(Zone zone);

  void deleteZone(int zoneId);

  void createDevice(DeviceDescriptor device);

  void readDevice(int deviceId, Callback<DeviceDescriptor> callback);

  void updateDevice(DeviceDescriptor device);

  void deleteDevice(int deviceId);
}