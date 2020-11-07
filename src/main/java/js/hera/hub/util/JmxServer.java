package js.hera.hub.util;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;

public class JmxServer
{
  private static final Log log = LogFactory.getLog(JmxServer.class);

  private final int port;

  public JmxServer()
  {
    log.trace("JmxServer()");
    MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
    String connectorPort;
    try {
      Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
      connectorPort = objectNames.iterator().next().getKeyProperty("port");
    }
    catch(MalformedObjectNameException e) {
      log.error(e);
      throw new BugError("Could not detect listening port from server management bean. Server abort.");
    }

    try {
      port = Integer.parseInt(connectorPort);
    }
    catch(NumberFormatException unused) {
      throw new BugError("Invalid number format |%s| for HTTP connector port on server management bean.", connectorPort);
    }
  }

  public int getPort()
  {
    return port;
  }
}
