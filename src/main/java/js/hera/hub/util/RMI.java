package js.hera.hub.util;

import java.lang.reflect.Type;

import js.net.client.HttpRmiTransaction;
import js.util.Strings;

public class RMI
{
  public static Object exec(String hostName, String methodName, Object[] arguments, Type returnType) throws Exception
  {
    HttpRmiTransaction rmi = HttpRmiTransaction.getInstance(Strings.concat("http://", hostName, ".local"));
    rmi.setConnectionTimeout(4000);
    rmi.setReadTimeout(8000);

    rmi.setMethod("js.hera.dev.HostSystem", methodName);
    rmi.setArguments(arguments);
    rmi.setReturnType(returnType);

    return rmi.exec(null);
  }
}
