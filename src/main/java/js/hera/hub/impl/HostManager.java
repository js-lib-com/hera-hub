package js.hera.hub.impl;

import java.util.List;

import js.hera.auto.util.Files;
import js.hera.hub.MessageBroker;
import js.hera.hub.dao.Dao;
import js.hera.hub.model.Host;
import js.hera.hub.util.JmxServer;
import js.hera.hub.util.NetInterfaces;
import js.hera.hub.util.RMI;
import js.lang.AbstractLooper;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.core.AppContext;

public class HostManager extends AbstractLooper
{
  private static final Log log = LogFactory.getLog(HostManager.class);

  private static final int LOOP_PERIOD = 60 * 1000;

  private final AppContext context;
  private final Dao dao;
  private final JmxServer jmxServer;
  private final NetInterfaces netInterfaces;
  private final List<Host> hosts;

  protected HostManager(AppContext context)
  {
    super(LOOP_PERIOD);
    log.trace("HostManager(AppContext)");

    this.context = context;
    this.dao = context.getInstance(Dao.class);
    this.jmxServer = context.getInstance(JmxServer.class);
    this.netInterfaces = context.getInstance(NetInterfaces.class);

    this.hosts = dao.getHosts();
  }

  @Override
  public void postConstruct() throws Exception
  {
    super.postConstruct();
    subscribe();
  }

  public List<Host> subscribe()
  {
    for(Host host : hosts) {
      host.setActive(subscribe(host.getName()));
      dao.updateHost(host);
    }
    return hosts;
  }

  @Override
  public void loop() throws Exception
  {
    log.trace("loop()");
  }

  private boolean subscribe(String hostName)
  {
    log.trace("subscribeDeviceHostSystem(String): %s", hostName);

    // http://192.168.0.3:8080/hera/js/hera/hub/impl/MessageBroker/publish.rmi

    StringBuilder messageBrokerURL = new StringBuilder();
    messageBrokerURL.append("http://");
    messageBrokerURL.append(netInterfaces.getInterfaceAddress(hostName));
    messageBrokerURL.append(':');
    messageBrokerURL.append(jmxServer.getPort());
    messageBrokerURL.append('/');

    messageBrokerURL.append(context.getAppName());
    messageBrokerURL.append('/');

    messageBrokerURL.append(Files.dot2urlpath(MessageBroker.class.getName()));
    messageBrokerURL.append('/');
    messageBrokerURL.append("publish.rmi");

    try {
      RMI.exec(hostName, "subscribe", new Object[]
      {
          messageBrokerURL.toString()
      }, Void.class);
    }
    catch(Throwable e) {
      log.error(e);
      return false;
    }
    return true;
  }
}
