package js.hera.hub.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.container.http.form.UploadedFile;
import com.jslib.io.FilesIterator;
import com.jslib.util.Files;
import com.jslib.util.Params;
import com.jslib.util.Strings;

import jakarta.inject.Inject;
import js.hera.dev.Actuator;
import js.hera.dev.BinaryLight;
import js.hera.dev.ColorLED;
import js.hera.dev.ContactSwitch;
import js.hera.dev.DHTSensor;
import js.hera.dev.Device;
import js.hera.dev.LightDimmer;
import js.hera.dev.LightSensor;
import js.hera.dev.MotionLight;
import js.hera.dev.NeoPixel;
import js.hera.dev.PowerMeter;
import js.hera.dev.RadioSwitch;
import js.hera.dev.RollerBlinds;
import js.hera.dev.TemperatureSensor;
import js.hera.dev.Thermostat;
import js.hera.dev.ThermostatSensor;
import js.hera.hub.Application;
import js.hera.hub.Service;
import js.hera.hub.dao.Dao;
import js.hera.hub.model.DeviceCategory;
import js.hera.hub.model.DeviceDTO;
import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.Host;
import js.hera.hub.model.Icon;
import js.hera.hub.model.PowerMeterValue;
import js.hera.hub.model.SelectItem;
import js.hera.hub.model.SystemDescriptor;
import js.hera.hub.model.Zone;

/**
 * Application service.
 * 
 * @author Iulian Rotaru
 */
final class ServiceImpl implements Service
{
  private static final Log log = LogFactory.getLog(ServiceImpl.class);

  private final Application app;
  private final Dao dao;
  private final HostManager hostManager;

  @Inject
  public ServiceImpl(Application app, Dao dao, HostManager hostManager) throws IOException
  {
    log.trace("ServiceImpl(Application, Dao, HostManager)");
    this.app = app;
    this.dao = dao;
    this.hostManager = hostManager;
  }

  // ------------------------------------------------------
  // HERA Smart Hub services

  @Override
  public SystemDescriptor getSystemDescriptor()
  {
    SystemDescriptor systemDescriptor = new SystemDescriptor();
    systemDescriptor.setZones(dao.getZones());
    systemDescriptor.setDeviceCategories(dao.getDeviceCategories());
    systemDescriptor.setDeviceDescriptors(dao.getDeviceDescriptors());
    systemDescriptor.setIconNames(getIconNames());
    return systemDescriptor;
  }

  @Override
  public Object invoke(String[] parameters) throws Throwable
  {
    String deviceName = parameters[0];
    String actionName = parameters[1];
    Object[] arguments = parameters.length == 3 ? new Object[]
    {
        parameters[2]
    } : new Object[0];
    
    return invokeDeviceAction(deviceName, actionName, arguments);
  }
  
  @Override
  public Object invokeDeviceAction(String deviceName, String actionName, Object[] arguments) throws Throwable
  {
    Params.notEmpty(deviceName, "Device name");
    Params.notEmpty(actionName, "Action name");

    log.trace("invokeDeviceAction(String, String, Object...)");
    log.debug("deviceName=|%s| actionName=|%s| arguments=%s", deviceName, actionName, arguments);

    DeviceDescriptor descriptor = dao.getDeviceDescriptor(deviceName);
    if(descriptor == null) {
      log.error("Missing descriptor for device |%s|.", deviceName);
      return null;
    }
    Host host = dao.getHost(descriptor.getHostId());
    if(host == null) {
      log.warn("Not configured host. Device |%s| is unreachable.", descriptor.getName());
      return null;
    }
    if(!host.isActive()) {
      log.warn("Inactive host |%s|. Device |%s| is unreachable.", host.getName(), descriptor.getName());
      return null;
    }

    DeviceActionProxy proxy = ActionProxyFactory.createActionProxy(descriptor);
    return proxy.exec(dao.getHost(descriptor.getHostId()).getName(), descriptor, actionName, arguments);
  }

  // ------------------------------------------------------
  // HERA Admin Console services

  @Override
  public List<DeviceDescriptor> getDevicesByZone(int zoneId)
  {
    return dao.getDevicesByZone(zoneId);
  }

  @Override
  public List<DeviceDescriptor> getDevicesByCategory(int categoryId)
  {
    return dao.getDevicesByCategory(categoryId);
  }

  @Override
  public Zone createZone(Zone zone)
  {
    Params.EQ(zone.getId(), 0, "Zone ID");
    dao.createZone(zone);
    // after dao create, zone id is updated
    return zone;
  }

  @Override
  public DeviceCategory createCategory(DeviceCategory category)
  {
    Params.EQ(category.getId(), 0, "Category ID");
    dao.createCategory(category);
    // after dao create, zone id is updated
    return category;
  }

  @Override
  public Zone readZone(int zoneId)
  {
    return dao.readZone(zoneId);
  }

  @Override
  public Zone updateZone(Zone zone)
  {
    dao.updateZone(zone);
    return zone;
  }

  @Override
  public DeviceCategory updateCategory(DeviceCategory category)
  {
    dao.updateCategory(category);
    return category;
  }

  @Override
  public void deleteZone(int zoneId)
  {
    dao.deleteZone(zoneId);
  }

  @Override
  public Host createHost(Host host)
  {
    Params.EQ(host.getId(), 0, "Host ID");
    dao.createHost(host);
    // after dao create, host id is updated
    return host;
  }

  @Override
  public Host updateHost(Host host)
  {
    dao.updateHost(host);
    return host;
  }

  @Override
  public List<Host> subscribeHosts()
  {
    return hostManager.subscribe();
  }

  @Override
  public void deleteHost(int hostId)
  {
    dao.deleteHost(hostId);
  }

  @Override
  public DeviceDTO createDevice(DeviceDescriptor device)
  {
    Params.EQ(device.getId(), 0, "Device ID");
    dao.createDevice(device);
    // after dao create, device id is updated
    return new DeviceDTO(dao, device);
  }

  @Override
  public DeviceDescriptor readDevice(int deviceId)
  {
    return dao.readDevice(deviceId);
  }

  @Override
  public DeviceDTO updateDevice(DeviceDescriptor device)
  {
    DeviceDescriptor persistedInstance = dao.getDevice(device.getId());
    persistedInstance.update(device);
    dao.updateDevice(persistedInstance);
    return new DeviceDTO(dao, device);
  }

  @Override
  public void deleteDevice(int deviceId)
  {
    dao.deleteDevice(deviceId);
  }

  @Override
  public List<Zone> getZones()
  {
    List<Zone> zones = dao.getZones();
    for(Zone zone : zones) {
      zone.setDevicesCount(dao.getDevicesCountByZone(zone.getId()));
    }
    return zones;
  }

  @Override
  public List<DeviceCategory> getCategories()
  {
    List<DeviceCategory> categories = dao.getDeviceCategories();
    for(DeviceCategory category : categories) {
      category.setDevicesCount(dao.getDevicesCountByCategory(category.getId()));
    }
    return categories;
  }

  @Override
  public List<DeviceDTO> getDevices()
  {
    List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
    for(DeviceDescriptor descriptor : dao.getDeviceDescriptors()) {
      devices.add(new DeviceDTO(dao, descriptor));
    }
    return devices;
  }

  @Override
  public List<SelectItem> getZoneItems()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for(Zone zone : dao.getZones()) {
      items.add(new SelectItem(zone));
    }
    return items;
  }

  @Override
  public List<SelectItem> getCategoryItems()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for(DeviceCategory category : dao.getDeviceCategories()) {
      items.add(new SelectItem(category));
    }
    return items;
  }

  @Override
  public List<SelectItem> getHostnameItems()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for(Host host : dao.getHosts()) {
      items.add(new SelectItem(host));
    }
    return items;
  }

  private static List<Class<? extends Device>> DEVICE_CLASSES = new ArrayList<Class<? extends Device>>();
  static {
    DEVICE_CLASSES.add(Actuator.class);
    DEVICE_CLASSES.add(BinaryLight.class);
    DEVICE_CLASSES.add(ColorLED.class);
    DEVICE_CLASSES.add(NeoPixel.class);
    DEVICE_CLASSES.add(ContactSwitch.class);
    DEVICE_CLASSES.add(DHTSensor.class);
    DEVICE_CLASSES.add(LightDimmer.class);
    DEVICE_CLASSES.add(LightSensor.class);
    DEVICE_CLASSES.add(MotionLight.class);
    DEVICE_CLASSES.add(PowerMeter.class);
    DEVICE_CLASSES.add(RadioSwitch.class);
    DEVICE_CLASSES.add(TemperatureSensor.class);
    DEVICE_CLASSES.add(Thermostat.class);
    DEVICE_CLASSES.add(ThermostatSensor.class);
    DEVICE_CLASSES.add(RollerBlinds.class);
  }

  @Override
  public List<Class<? extends Device>> getDeviceClasses()
  {
    return DEVICE_CLASSES;
  }

  @Override
  public List<DeviceDescriptor> getDevicesByCategoryName(String categoryName)
  {
    DeviceCategory category = dao.getDeviceCategoryByName(categoryName);
    if(category == null) {
      return Collections.emptyList();
    }
    return getDevicesByCategory(category.getId());
  }

  @Override
  public void deleteCategory(int categoryId)
  {
    dao.deleteCategory(categoryId);
  }

  @Override
  public List<Host> getHosts()
  {
    return dao.getHosts();
  }

  @Override
  public List<Icon> getIcons()
  {
    List<Icon> icons = new ArrayList<Icon>();
    int id = 1;
    for(File file : FilesIterator.getRelativeIterator(getIconsDir())) {
      icons.add(new Icon(id++, Files.basename(file), "/icons/" + file.getName()));
    }
    return icons;
  }

  @Override
  public void uploadIcon(UploadedFile icon) throws IOException
  {
    log.debug("Upload icon file |%s| with type |%s| and name |%s|", icon.getFileName(), icon.getContentType(), icon.getName());
    icon.moveTo(getIconsDir());
  }

  @Override
  public List<String> getIconNames()
  {
    List<String> names = new ArrayList<String>();
    for(File file : FilesIterator.getRelativeIterator(getIconsDir())) {
      names.add(Files.basename(file));
    }
    Collections.sort(names);
    return names;
  }

  @Override
  public void updateIconName(String iconName, String newName)
  {
    log.debug("Update icon |%s| name to |%s|.", iconName, newName);

    for(Zone zone : dao.getZonesByIcon(iconName)) {
      zone.setIcon(newName);
      dao.updateZone(zone);
    }
    for(DeviceCategory category : dao.getCategoriesByIcon(iconName)) {
      category.setIcon(newName);
      dao.updateCategory(category);
    }

    File iconsDir = getIconsDir();
    File icon = new File(iconsDir, iconName + ".png");
    File newIcon = new File(iconsDir, newName + ".png");
    icon.renameTo(newIcon);
  }

  @Override
  public boolean isIconUsed(String iconName)
  {
    if(!dao.getZonesByIcon(iconName).isEmpty()) {
      return true;
    }
    if(!dao.getCategoriesByIcon(iconName).isEmpty()) {
      return true;
    }
    return false;
  }

  @Override
  public void removeIcon(String iconName)
  {
    log.debug("Remove icon |%s|.", iconName);
    File iconsDir = getIconsDir();
    File icon = new File(iconsDir, iconName + ".png");
    icon.delete();
  }

  private File getIconsDir()
  {
    File iconsDir = new File(System.getProperty("catalina.base") + "/webapps");
    iconsDir = new File(iconsDir, "icons");
    if(!iconsDir.isDirectory()) {
      iconsDir.mkdirs();
    }
    return iconsDir;
  }

  @Override
  public PowerMeterValue getPowerMeterValue() throws NumberFormatException, IOException
  {
    File energyIndex = app.getAppFile("energy-index");
    return new PowerMeterValue(Double.parseDouble(Strings.load(energyIndex)), app.getPowerValue());
  }
}
