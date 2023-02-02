package js.hera.hub.impl;

import java.util.function.Consumer;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.net.client.HttpRmiTransaction;
import com.jslib.util.Strings;

import js.hera.hub.model.DeviceDescriptor;

public class HeraActionProxy implements DeviceActionProxy
{
  private static final Log log = LogFactory.getLog(HeraActionProxy.class);

  public Object exec(String hostname, DeviceDescriptor descriptor, String actionName, Object... arguments) throws Throwable
  {
    log.trace("exec(String hostname, DeviceDescriptor descriptor, String actionName, Object... arguments)");
    
    HttpRmiTransaction rmi = HttpRmiTransaction.getInstance(Strings.concat("http://", descriptor.getHostname(), ".local"));
    rmi.setConnectionTimeout(4000);
    rmi.setReadTimeout(8000);

    rmi.setExceptionHandler(new Consumer<Throwable>()
    {
      @Override
      public void accept(Throwable t)
      {
        // log.error(throwable);
        // HeraProxyHandler.this.active = false;
      }
    });

    int parametersCount = arguments != null ? arguments.length : 0;

    Object[] remoteArguments = new Object[parametersCount + 2];
    remoteArguments[0] = descriptor.getName();
    remoteArguments[1] = actionName;
    for(int i = 0; i < parametersCount; ++i) {
      remoteArguments[i + 2] = arguments[i];
    }

    rmi.setMethod("js.hera.dev.HostSystem", "invoke");
    rmi.setArguments(remoteArguments);
    rmi.setReturnType(DeviceMethods.getReturnType(descriptor.getDeviceClass(), actionName));

    // null callback to force synchronous execution
    return rmi.exec(null);
  }
}
