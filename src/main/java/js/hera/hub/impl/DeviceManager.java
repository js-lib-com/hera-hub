package js.hera.hub.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import js.hera.DeviceAdvertise;
import js.hera.DeviceDiscoveryMessage;
import js.hera.HostAdvertise;
import js.hera.dev.HostSystem;
import js.hera.hub.MessageBroker;
import js.hera.hub.dao.Dao;
import js.hera.hub.model.DeviceDescriptor;
import js.hera.hub.model.Host;
import js.lang.AbstractLooper;
import js.lang.BugError;
import js.lang.ManagedLifeCycle;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.core.AppContext;
import js.tiny.container.core.Factory;
import js.util.Files;

/**
 * Implementation for device manager.
 * 
 * @author Iulian Rotaru
 */
final class DeviceManager extends AbstractLooper implements ManagedLifeCycle, HostAdvertiseListener
{
  private static final Log log = LogFactory.getLog(DeviceManager.class);

  private static final int DISCOVERY_PERIOD = 30000;

  private final AppContext context;
  private final DiscoveryHandler discoveryHandler;
  private final NetInterfaces netInterfaces;

  public DeviceManager(AppContext context)
  {
    super(DISCOVERY_PERIOD);
    log.trace("DeviceManager()");
    this.context = context;
    this.discoveryHandler = new HeraDiscoveryHandler();
    this.netInterfaces = NetInterfaces.getInstance();
  }

  @Override
  public void postConstruct() throws Exception
  {
    super.postConstruct();
    log.trace("postConstruct()");
    discoveryHandler.setListener(this);
    discoveryHandler.start();
  }

  /**
   * Stop discovery connector thread. Send shutdown datagram so that thread main loop interrupt itself. Note that this
   * method blocks till thread is actually stopped.
   */
  @Override
  public void preDestroy() throws Exception
  {
    log.trace("preDestroy()");
    discoveryHandler.stop();
    super.preDestroy();
  }

  @Override
  public void onDeviceAdvertise(HostAdvertise hostAdvertise)
  {
    log.trace("onDeviceAdvertise(HostAdvertise) - %s", hostAdvertise.getHostURL());
    Dao dao = Factory.getInstance(Dao.class);

    Host host = dao.getHostByName(hostAdvertise.getHostName());
    if(host == null) {
      host = new Host(hostAdvertise);
    }
    else {
      host.update(hostAdvertise);
    }
    dao.updateHost(host);
    subscribeDeviceHostSystem(hostAdvertise.getHostURL());

    for(DeviceAdvertise device : hostAdvertise.getDevices()) {
      log.debug("Device class: %s", device.getDeviceClass());
      DeviceDescriptor descriptor = dao.getDeviceDescriptor(device.getName());
      if(descriptor == null) {
        log.warn("Advertised device |%s| is not configured. Ignore it.", device);
        continue;
      }
      descriptor.setDeviceClass(device.getDeviceClass());
      descriptor.setHostURL(hostAdvertise.getHostURL());
    }
  }

  @Override
  public void loop() throws Exception
  {
    // broadcast discovery message periodically
    
    DeviceDiscoveryMessage deviceDiscoveryMessage = new DeviceDiscoveryMessage();
    deviceDiscoveryMessage.setTimestamp(System.currentTimeMillis());

    try {
      discoveryHandler.broadcastDiscoveryMessage(deviceDiscoveryMessage);
    }
    catch(IOException e) {
      log.error("IO exception on broadcast discovery message: %s", e.getMessage());
    }
    catch(Exception e) {
      log.error("Unexpected exception on broadcast discovery message: %s", e.getMessage());
    }
  }

  private void subscribeDeviceHostSystem(URL hostURL)
  {
    log.trace("subscribeDeviceHostSystem(URL): %s", hostURL);

    StringBuilder messageBrokerURL = new StringBuilder();
    messageBrokerURL.append("http://");
    messageBrokerURL.append(netInterfaces.getInterfaceAddress(hostURL));
    messageBrokerURL.append(':');
    messageBrokerURL.append(JmxServer.getInstance().getPort());
    messageBrokerURL.append('/');

    messageBrokerURL.append(context.getAppName());
    messageBrokerURL.append('/');

    messageBrokerURL.append(Files.dot2urlpath(MessageBroker.class.getName()));
    messageBrokerURL.append('/');
    messageBrokerURL.append("publish.rmi");

    HostSystem host = Factory.getRemoteInstance(hostURL.toExternalForm(), HostSystem.class);
    try {
      host.subscribe(new URL(messageBrokerURL.toString()));
    }
    catch(MalformedURLException unused) {
      throw new BugError("Bad harcoded URL |%s|.", messageBrokerURL);
    }
  }
}
