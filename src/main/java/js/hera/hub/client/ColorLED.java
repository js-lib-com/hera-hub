package js.hera.hub.client;

import js.hera.dev.Device;
import js.lang.Callback;

public interface ColorLED extends Device
{
  void setColor(int color);

  void getColor(Callback<Integer> callback);
}
