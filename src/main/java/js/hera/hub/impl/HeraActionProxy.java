package js.hera.hub.impl;

import js.hera.hub.model.DeviceDescriptor;
import js.lang.Callback;
import js.net.client.HttpRmiTransaction;
import js.util.Strings;

public class HeraActionProxy implements DeviceActionProxy
{
  public Object exec(String hostname, DeviceDescriptor descriptor, String actionName, Object... arguments) throws Exception
  {
    HttpRmiTransaction rmi = HttpRmiTransaction.getInstance(Strings.concat("http://", descriptor.getHostname(), ".local"));
    rmi.setConnectionTimeout(4000);
    rmi.setReadTimeout(8000);

    rmi.setExceptionHandler(new Callback<Throwable>()
    {
      @Override
      public void handle(Throwable throwable)
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
