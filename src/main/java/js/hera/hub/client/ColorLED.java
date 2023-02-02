package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

public interface ColorLED extends Device
{
  void setColor(int color);

  void getColor(Consumer<Integer> callback);
}
