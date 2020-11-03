package js.hera.hub.client;

import js.hera.dev.Device;
import js.lang.Callback;

public interface MotionLight extends Device
{
  void turnON();

  void turnOFF();

  void getState(Callback<Boolean> callback);
}
