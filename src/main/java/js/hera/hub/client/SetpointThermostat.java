package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

/**
 * Thermostat with setpoint.
 * 
 * @author Iulian Rotaru
 */
public interface SetpointThermostat extends Device
{
  void setSetpoint(Double setpoint);

  void getSetpoint(Consumer<Double> consumer);

  void getTemperature(Consumer<Double> consumer);

  void getState(Consumer<Boolean> consumer);
}
