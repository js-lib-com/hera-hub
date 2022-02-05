package js.hera.hub.impl;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import js.hera.hub.Application;
import js.hera.hub.MessageBroker;
import js.hera.hub.dao.Dao;
import js.hera.hub.model.Host;
import js.hera.hub.util.NetInterfaces;
import js.hera.hub.util.RMI;
import js.lang.AbstractLooper;
import js.log.Log;
import js.log.LogFactory;
import js.util.Files;

@ApplicationScoped
@Startup
public class HostManager extends AbstractLooper
{
  private static final Log log = LogFactory.getLog(HostManager.class);

  private static final int LOOP_PERIOD = 60 * 1000;

  private final Application app;
  private final Dao dao;
  private final NetInterfaces netInterfaces;
  private final Timer timer = new Timer("SubscribeTimer");

  @Inject
  public HostManager(Application app, Dao dao, NetInterfaces netInterfaces)
  {
    super(LOOP_PERIOD);
    log.trace("HostManager(Application, Dao, NetInterfaces)");

    this.app = app;
    this.dao = dao;
    this.netInterfaces = netInterfaces;
  }

  @Override
  public void postConstruct() throws Exception
  {
    super.postConstruct();
    subscribe();

    long delay = 5 * 60 * 1000;
    timer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        subscribe();
      }
    }, delay);
  }

  public List<Host> subscribe()
  {
    List<Host> hosts = dao.getHosts();
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
    hostName += ".local";
    log.trace("subscribeDeviceHostSystem(String): %s", hostName);

    // http://192.168.0.3:8080/hub/js/hera/hub/impl/MessageBroker/publish.rmi

    StringBuilder messageBrokerURL = new StringBuilder();
    messageBrokerURL.append("http://");
    messageBrokerURL.append(netInterfaces.getInterfaceAddress(hostName));
    messageBrokerURL.append(':');
    messageBrokerURL.append(8080);
    messageBrokerURL.append('/');

    messageBrokerURL.append(app.getAppName());
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
