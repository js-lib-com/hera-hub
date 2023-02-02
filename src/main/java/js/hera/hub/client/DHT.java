package js.hera.hub.client;

import java.util.function.Consumer;

import js.hera.dev.Device;

public interface DHT extends Device
{
  Value getValue(Consumer<DHT.Value> callback);

  public static class Value
  {
    private float humidity;
    private float temperature;

    public float getHumidity()
    {
      return humidity;
    }

    public void setHumidity(float humidity)
    {
      this.humidity = humidity;
    }

    public float getTemperature()
    {
      return temperature;
    }

    public void setTemperature(float temperature)
    {
      this.temperature = temperature;
    }
  }
}
