package js.hera.hub.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import js.hera.dev.Device;
import js.util.Classes;

public final class DeviceMethods
{
  private static final Map<Class<? extends Device>, Map<String, Type>> classCache = new HashMap<Class<? extends Device>, Map<String, Type>>();

  public static Type getReturnType(Class<? extends Device> deviceClass, String methodName) throws NoSuchMethodException
  {
    Map<String, Type> typeCache = classCache.get(deviceClass);
    if(typeCache == null) {
      typeCache = new HashMap<String, Type>();
      classCache.put(deviceClass, typeCache);
    }
    Type type = typeCache.get(methodName);
    if(type == null) {
      Method method = Classes.findMethod(deviceClass, methodName);
      type = method.getGenericReturnType();
      typeCache.put(methodName, type);
    }
    return type;
  }
}
