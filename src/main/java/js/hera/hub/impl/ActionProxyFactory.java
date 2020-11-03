package js.hera.hub.impl;

import js.hera.hub.model.DeviceDescriptor;

public class ActionProxyFactory
{
  public static DeviceActionProxy createActionProxy(DeviceDescriptor descriptor)
  {
    // TODO: add logic to discriminate UPNP from HERA
    return new HeraActionProxy();
  }
}
