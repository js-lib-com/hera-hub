package js.hera.hub.util;

import java.lang.reflect.Type;

import js.net.client.HttpRmiTransaction;

public class RMI
{
  /**
   * Invoke remote method.
   * 
   * @param hostName qualified host name where remote method is hosted,
   * @param methodName remote method name,
   * @param arguments invocation arguments list,
   * @param returnType returned type.
   * @return remote value.
   * @throws Exception if anything goes wrong.
   */
  public static Object exec(String hostName, String methodName, Object[] arguments, Type returnType) throws Exception
  {
    HttpRmiTransaction rmi = HttpRmiTransaction.getInstance("http://" + hostName);
    rmi.setConnectionTimeout(4000);
    rmi.setReadTimeout(8000);

    rmi.setMethod("js.hera.dev.HostSystem", methodName);
    rmi.setArguments(arguments);
    rmi.setReturnType(returnType);

    return rmi.exec(null);
  }
}
