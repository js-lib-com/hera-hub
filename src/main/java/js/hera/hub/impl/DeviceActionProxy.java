package js.hera.hub.impl;

import js.hera.hub.model.DeviceDescriptor;

public interface DeviceActionProxy
{
  Object exec(DeviceDescriptor descriptor, String actionName, Object... arguments) throws Exception;
}
