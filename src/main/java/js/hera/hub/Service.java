package js.hera.hub;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ws.rs.Path;

import com.jslib.automata.ActionDescriptor;
import com.jslib.automata.DeviceActionHandler;
import com.jslib.automata.Rule;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
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
import js.tiny.container.http.form.UploadedFile;

@ApplicationScoped
@Remote
@PermitAll
public interface Service extends DeviceActionHandler
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
  @POST
  @Path("invoke/{deviceName}/{actionName}/{args}")
  Object invokeDeviceAction(@PathParam("deviceName") String deviceName, @PathParam("actionName") String actionName, @PathParam("args") Object[] args)
      throws Exception;

  // ------------------------------------------------------
  // HERA Admin Console services

  List<DeviceDescriptor> getDevicesByZone(int zoneId);

  List<DeviceDescriptor> getDevicesByCategory(int categoryId);

  Zone createZone(Zone zone);

  DeviceCategory createCategory(DeviceCategory category);

  Zone readZone(int zoneId);

  Zone updateZone(Zone zone);

  Host createHost(Host host);

  Host updateHost(Host host);

  @DELETE
  @Path("host/{id}")
  void deleteHost(@PathParam("id") int hostId);

  List<Host> subscribeHosts();

  DeviceCategory updateCategory(DeviceCategory category);

  @DELETE
  @Path("zone/{id}")
  void deleteZone(@PathParam("id") int zoneId);

  DeviceDTO createDevice(DeviceDescriptor device);

  @GET
  @Path("device/{id}")
  DeviceDescriptor readDevice(@PathParam("id") int deviceId);

  DeviceDTO updateDevice(DeviceDescriptor device);

  @DELETE
  @Path("device/{id}")
  void deleteDevice(@PathParam("id") int deviceId);

  // ------------------------------------------------------
  // web app only

  List<Zone> getZones();

  List<DeviceCategory> getCategories();

  List<DeviceDTO> getDevices();

  @POST
  @Path("zones")
  List<SelectItem> getZoneItems();

  @POST
  @Path("categories")
  List<SelectItem> getCategoryItems();

  @POST
  @Path("hostnames")
  List<SelectItem> getHostnameItems();

  @POST
  @Path("device-classes")
  List<Class<? extends Device>> getDeviceClasses();

  @GET
  @Path("binary-lights")
  List<String> getBinaryLights();

  @GET
  @Path("devices/{category}")
  List<DeviceDescriptor> getDevicesByCategoryName(@PathParam("category") String categoryName);

  void deleteCategory(int categoryId);

  List<Host> getHosts();

  List<Icon> getIcons();

  void uploadIcon(UploadedFile icon) throws IOException;

  @POST
  @Path("icons")
  List<String> getIconNames();

  @PUT
  @Path("icon/{name}/{newName}")
  void updateIconName(String name, String newName);

  boolean isIconUsed(String iconName);

  @DELETE
  @Path("icon/{iconName}")
  void removeIcon(@PathParam("iconName") String iconName);

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