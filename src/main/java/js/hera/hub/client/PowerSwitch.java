package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

/**
 * Switch power on a not specified consumer.
 * 
 * @author Iulian Rotaru
 */
public interface PowerSwitch extends Device
{
  void turnON();

  void turnOFF();

  /**
   * Get switch state. If switch is not active callback is not invoked and attempt to read is recorded to supervisor.
   * 
   * @return
   */
  void getState(Consumer<Boolean> callback);
}
