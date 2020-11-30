package js.hera.auto.engine;

import com.jslib.automata.DeviceAction;

public class TemperatureSensor extends DeviceAction
{
  private double thermostatSensorValue;

  protected void update() throws Exception
  {
    System.out.printf("Thermostat Sensor Value: %.02f\n", thermostatSensorValue);

    RMI("http://Android.local:8080/sync", "com.jslib.hera.agent.Controller", "hello", new Object[]
    {
        String.format("Master room temperature is %.02f.", thermostatSensorValue)
    }, null);
  }
}
