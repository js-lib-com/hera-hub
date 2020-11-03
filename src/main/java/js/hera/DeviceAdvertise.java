package js.hera;

import js.hera.dev.Device;

public class DeviceAdvertise
{
  private Class<? extends Device> deviceClass;
  private String name;

  public DeviceAdvertise()
  {
  }

  public DeviceAdvertise(Class<? extends Device> deviceClass, String name)
  {
    this.deviceClass = deviceClass;
    this.name = name;
  }

  public Class<? extends Device> getDeviceClass()
  {
    return deviceClass;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
