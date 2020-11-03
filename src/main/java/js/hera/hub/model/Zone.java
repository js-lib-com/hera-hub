package js.hera.hub.model;

public class Zone
{
  private int id;
  private String name;
  private String display;
  private String icon;
  private int devicesCount;

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDisplay()
  {
    return display;
  }

  public void setDisplay(String display)
  {
    this.display = display;
  }

  public String getIcon()
  {
    return icon;
  }

  public void setIcon(String icon)
  {
    this.icon = icon;
  }

  public int getDevicesCount()
  {
    return devicesCount;
  }

  public void setDevicesCount(int devicesCount)
  {
    this.devicesCount = devicesCount;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
