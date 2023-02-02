package js.hera.hub.dao;

import java.util.List;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.util.Params;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import js.hera.hub.Application;
import js.hera.hub.model.DeviceCategory;
import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.Host;
import js.hera.hub.model.User;
import js.hera.hub.model.Zone;

/**
 * Implementation for Dao managed class.
 * 
 * @author Iulian Rotaru
 */
@ManagedBean
public final class DaoImpl implements Dao
{
  private static final Log log = LogFactory.getLog(DaoImpl.class);

  private static final String STORE_USER = "user.store";
  private static final String STORE_HOST = "host.store";
  private static final String STORE_ZONE = "zone.store";
  private static final String STORE_DEVICE_CATEGORY = "device-category.store";
  private static final String STORE_DEVICE_DESCRIPTOR = "device-descriptor.store";

  private final Store<User> userStore;
  private final Store<Host> hostStore;
  private final Store<Zone> zoneStore;
  private final Store<DeviceCategory> categoryStore;
  private final Store<DeviceDescriptor> deviceStore;

  @Inject
  public DaoImpl(Application app)
  {
    log.trace("DaoImpl(Application)");
    userStore = new Store<User>(app, this, STORE_USER, User.class);
    hostStore = new Store<Host>(app, this, STORE_HOST, Host.class);
    zoneStore = new Store<Zone>(app, this, STORE_ZONE, Zone.class);
    categoryStore = new Store<DeviceCategory>(app, this, STORE_DEVICE_CATEGORY, DeviceCategory.class);
    deviceStore = new Store<DeviceDescriptor>(app, this, STORE_DEVICE_DESCRIPTOR, DeviceDescriptor.class);
  }

  @PostConstruct
  public void postConstruct()
  {
    log.trace("postConstruct()");
    userStore.load();
    hostStore.load();
    zoneStore.load();
    categoryStore.load();
    deviceStore.load();
  }

  @PreDestroy
  public void preDestroy()
  {
    log.trace("preDestroy()");
    userStore.save();
    hostStore.save();
    zoneStore.save();
    categoryStore.save();
    deviceStore.save();
  }

  @Override
  public User getUserById(int userId)
  {
    return userStore.get(userId);
  }

  @Override
  public List<Zone> getZones()
  {
    return zoneStore.values();
  }

  @Override
  public List<DeviceCategory> getDeviceCategories()
  {
    return categoryStore.values();
  }

  @Override
  public List<DeviceDescriptor> getDeviceDescriptors()
  {
    return deviceStore.values();
  }

  @Override
  public List<DeviceDescriptor> getDevicesByZone(final int zoneId)
  {
    return deviceStore.values(device -> device.getZoneId() == zoneId);
  }

  @Override
  public int getDevicesCountByZone(int zoneId)
  {
    int devicesCount = 0;
    for(DeviceDescriptor device : deviceStore.values()) {
      if(device.getZoneId() == zoneId) {
        ++devicesCount;
      }
    }
    return devicesCount;
  }

  @Override
  public List<DeviceDescriptor> getDevicesByCategory(final int categoryId)
  {
    return deviceStore.values(device -> device.getCategoryId() == categoryId);
  }

  @Override
  public int getDevicesCountByCategory(int categoryId)
  {
    int devicesCount = 0;
    for(DeviceDescriptor device : deviceStore.values()) {
      if(device.getCategoryId() == categoryId) {
        ++devicesCount;
      }
    }
    return devicesCount;
  }

  @Override
  public DeviceDescriptor getDeviceDescriptor(String deviceName)
  {
    // it would be interesting to know how a database engines handle this lookup
    // for now this brute force solution is good enough
    for(DeviceDescriptor descriptor : deviceStore.values()) {
      if(descriptor.getName().equals(deviceName)) {
        return descriptor;
      }
    }
    return null;
  }

  @Override
  public void createZone(Zone zone)
  {
    zoneStore.put(zone.getId(), zone);
  }

  @Override
  public void createCategory(DeviceCategory category)
  {
    categoryStore.put(category.getId(), category);
  }

  @Override
  public Zone readZone(int zoneId)
  {
    return zoneStore.get(zoneId);
  }

  @Override
  public void updateZone(Zone zone)
  {
    zoneStore.put(zone.getId(), zone);
  }

  @Override
  public void updateCategory(DeviceCategory category)
  {
    categoryStore.put(category.getId(), category);
  }

  @Override
  public void deleteZone(int zoneId)
  {
    zoneStore.remove(zoneId);
  }

  @Override
  public void createDevice(DeviceDescriptor device)
  {
    deviceStore.put(device.getId(), device);
  }

  @Override
  public DeviceDescriptor readDevice(int deviceId)
  {
    return deviceStore.get(deviceId);
  }

  @Override
  public void updateDevice(DeviceDescriptor device)
  {
    deviceStore.put(device.getId(), device);
  }

  @Override
  public void deleteDevice(int deviceId)
  {
    deviceStore.remove(deviceId);
  }

  @Override
  public DeviceCategory getDeviceCategoryByName(final String categoryName)
  {
    return categoryStore.value(category -> category.getName().equals(categoryName));
  }

  @Override
  public void deleteCategory(int categoryId)
  {
    categoryStore.remove(categoryId);
  }

  @Override
  public Zone getZone(int zoneId)
  {
    return zoneStore.get(zoneId);
  }

  @Override
  public DeviceCategory getCategory(int categoryId)
  {
    return categoryStore.get(categoryId);
  }

  @Override
  public Host getHost(int hostId)
  {
    return hostStore.get(hostId);
  }

  @Override
  public DeviceDescriptor getDevice(int deviceId)
  {
    return deviceStore.get(deviceId);
  }

  @Override
  public List<Host> getHosts()
  {
    return hostStore.values();
  }

  @Override
  public Host getHostByName(final String name)
  {
    Params.notNull(name, "Host name");
    return hostStore.value(host -> name.equals(host.getName()));
  }

  @Override
  public void createHost(Host host)
  {
    hostStore.put(host.getId(), host);
  }

  @Override
  public void deleteHost(int hostId)
  {
    hostStore.remove(hostId);
  }

  @Override
  public void clearHosts()
  {
    hostStore.clear();
  }

  @Override
  public void updateHost(Host host)
  {
    hostStore.put(host.getId(), host);
  }

  @Override
  public List<Zone> getZonesByIcon(final String iconName)
  {
    Params.notNull(iconName, "Icon name");
    return zoneStore.values(zone -> iconName.equals(zone.getIcon()));
  }

  @Override
  public List<DeviceCategory> getCategoriesByIcon(final String iconName)
  {
    Params.notNull(iconName, "Icon name");
    return categoryStore.values(category -> iconName.equals(category.getIcon()));
  }
}
