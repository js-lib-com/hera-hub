package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

public interface ContactSwitch extends Device
{
  void isOpened(Consumer<Boolean> callback);
}
