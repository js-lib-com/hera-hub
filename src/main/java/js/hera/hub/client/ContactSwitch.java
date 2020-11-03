package js.hera.hub.client;

import js.hera.dev.Device;
import js.lang.Callback;

public interface ContactSwitch extends Device
{
  void isOpened(Callback<Boolean> callback);
}
