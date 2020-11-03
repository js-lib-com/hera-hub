package js.hera.hub;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import js.hera.auto.engine.ActionDescriptor;
import js.hera.auto.engine.Rule;
import js.hera.dev.Device;
import js.hera.hub.model.DeviceCategory;
import js.hera.hub.model.DeviceDTO;
import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.Host;
import js.hera.hub.model.Icon;
import js.hera.hub.model.PowerMeterValue;
import js.hera.hub.model.SelectItem;
import js.hera.hub.model.SystemDescriptor;
import js.hera.hub.model.Zone;
import js.tiny.container.annotation.Public;
import js.tiny.container.annotation.Remote;
import js.tiny.container.annotation.RequestPath;
import js.tiny.container.http.form.UploadedFile;

@Remote
@Public
public interface Service
{
  // ------------------------------------------------------
  // HERA Smart Hub services

  SystemDescriptor getSystemDescriptor();

  /**
   * Invoke synchronously named action on the remote device instance.
   * 
   * @param deviceName device instance name,
   * @param actionName action name to execute remotely,
   * @param arguments variable number of arguments requested by device action.
   * @return action return value.
   * @throws Exception
   */
  Object invokeDeviceAction(String deviceName, String actionName, Object... args) throws Exception;

  // ------------------------------------------------------
  // HERA Admin Console services

  List<DeviceDescriptor> getDevicesByZone(int zoneId);

  List<DeviceDescriptor> getDevicesByCategory(int categoryId);

  Zone createZone(Zone zone);

  DeviceCategory createCategory(DeviceCategory category);

  Zone readZone(int zoneId);

  Zone updateZone(Zone zone);

  DeviceCategory updateCategory(DeviceCategory category);

  void deleteZone(int zoneId);

  DeviceDTO createDevice(DeviceDescriptor device);

  DeviceDescriptor readDevice(int deviceId);

  DeviceDTO updateDevice(DeviceDescriptor device);

  void deleteDevice(int deviceId);

  // ------------------------------------------------------
  // web app only

  List<Zone> getZones();

  List<DeviceCategory> getCategories();

  List<DeviceDTO> getDevices();

  @RequestPath("zones")
  List<SelectItem> getZoneItems();

  @RequestPath("categories")
  List<SelectItem> getCategoryItems();

  @RequestPath("device-classes")
  List<Class<? extends Device>> getDeviceClasses();

  @RequestPath("binary-lights")
  List<String> getBinaryLights();

  List<DeviceDescriptor> getDevicesByCategoryName(String categoryName);

  void deleteCategory(int categoryId);

  List<Host> getHosts();

  List<Icon> getIcons();

  void uploadIcon(UploadedFile icon) throws IOException;

  @RequestPath("icons")
  List<String> getIconNames();

  void updateIconName(String name, String newName);

  boolean isIconUsed(String iconName);

  void removeIcon(String iconName);

  PowerMeterValue getPowerMeterValue() throws NumberFormatException, IOException;

  // ------------------------------------------------------
  // automata

  ActionDescriptor createActionCode(String actionDisplay) throws IOException;
  
  void saveAction(ActionDescriptor action) throws IOException, ClassNotFoundException;
  
  void removeAction(String actionClassName);

  void saveRule(Rule rule) throws ClassNotFoundException, IOException;

  void removeRule(String ruleName) throws IOException;
  
  Set<ActionDescriptor> getActions();
  
  Set<Rule> getRules();
}