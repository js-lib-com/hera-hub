package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

public interface MotionLight extends Device
{
  void turnON();

  void turnOFF();

  void getState(Consumer<Boolean> callback);
}
