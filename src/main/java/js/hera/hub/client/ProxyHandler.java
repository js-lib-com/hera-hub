package js.hera.hub.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import js.lang.BugError;
import js.lang.Callback;
import js.net.client.HttpRmiTransaction;
import js.util.Types;

public class ProxyHandler implements InvocationHandler
{
  private static final String PROXY_SERVICE = "js.hera.hub.Service";
  private static final String PROXY_METHOD = "invokeDeviceAction";

  private String implementationURL;
  private String deviceName;

  public ProxyHandler(String implementationURL, String deviceName)
  {
    this.implementationURL = implementationURL;
    this.deviceName = deviceName;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
  {
    if(method.getName().equals("toString")) {
      return proxy.getClass().getName();
    }

    // initialize return type; for void method uses string since dots responds with 'null' for void
    Type returnType = Types.isVoid(method.getReturnType()) ? String.class : method.getGenericReturnType();
    Callback<Object> callback = null;

    List<Object> remoteParameters = new ArrayList<Object>();
    if(!isEmpty(arguments)) {

      for(int i = 0; i < arguments.length; ++i) {
        if(!(arguments[i] instanceof Callback)) {
          remoteParameters.add(arguments[i]);
          continue;
        }

        // here argument is the callback
        // it is expected to have a single callback; if more, uses the last one
        assert callback == null;
        callback = (Callback<Object>)arguments[i];

        // if callback is present uses its parameterized type as return type for the remote method
        // extract type parameter from actual argument instance, not from method signature that can contain wild card
        Type callbackType = arguments[i].getClass().getGenericInterfaces()[0];
        if(!(callbackType instanceof ParameterizedType)) {
          throw new BugError("Missing callback generic type. Cannot infer return type for |%s|.", method);
        }
        returnType = ((ParameterizedType)callbackType).getActualTypeArguments()[0];
      }

    }

    HttpRmiTransaction rmi = HttpRmiTransaction.getInstance(implementationURL);
    rmi.setMethod(PROXY_SERVICE, PROXY_METHOD);

    Object[] remoteArguments = new Object[remoteParameters.isEmpty() ? 2 : 3];
    remoteArguments[0] = deviceName;
    remoteArguments[1] = method.getName();
    if(!remoteParameters.isEmpty()) {
      remoteArguments[2] = remoteParameters.toArray();
    }

    rmi.setArguments(remoteArguments);

    if(callback == null) {
      if(!Types.isVoid(method.getReturnType())) {
        // synchronous mode only if remote method has return value but there is no callback supplied
        rmi.setReturnType(method.getGenericReturnType());
        return rmi.exec(null);
      }

      callback = new Callback<Object>()
      {
        @Override
        public void handle(Object value)
        {
        }
      };
    }

    rmi.setReturnType(returnType);
    rmi.exec(callback);
    return null;
  }

  private static boolean isEmpty(Object[] arguments)
  {
    // excerpt from Java API regarding third argument of invocation handler 'invoke' method:
    // arguments - an array of objects containing the values of the arguments passed in the method invocation on the
    // proxy instance or null if interface method takes no arguments.
    // to be on safe side test both null and empty conditions

    return arguments == null || arguments.length == 0;
  }
}
