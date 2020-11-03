package js.hera.hub.client;

import java.lang.reflect.Proxy;

import js.hera.dev.Device;

public final class DeviceFactory
{
  @SuppressWarnings("unchecked")
  public static <T extends Device> T createDeviceProxy(String implementationURL, String deviceName, Class<T> deviceClass)
  {
    ProxyHandler proxyHandler = new ProxyHandler(implementationURL, deviceName);
    return (T)Proxy.newProxyInstance(deviceClass.getClassLoader(), new Class<?>[]
    {
      deviceClass
    }, proxyHandler);
  }
}
