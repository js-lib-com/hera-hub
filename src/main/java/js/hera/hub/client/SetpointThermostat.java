package js.hera.hub.client;

import js.hera.dev.Device;
import js.lang.Callback;

/**
 * Thermostat with setpoint.
 * 
 * @author Iulian Rotaru
 */
public interface SetpointThermostat extends Device
{
  void setSetpoint(Double setpoint);

  void getSetpoint(Callback<Double> callback);

  void getTemperature(Callback<Double> callback);

  void getState(Callback<Boolean> callback);
}
