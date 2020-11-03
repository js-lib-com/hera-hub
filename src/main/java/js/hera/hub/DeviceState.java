package js.hera.hub;

import js.lang.Event;

public class DeviceState implements Event
{
  private String deviceName;
  private double value;

  public String getDeviceName()
  {
    return deviceName;
  }

  public double getValue()
  {
    return value;
  }

  @Override
  public String toString()
  {
    return "DeviceState [deviceName=" + deviceName + ", value=" + value + "]";
  }
}
