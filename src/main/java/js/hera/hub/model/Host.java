package js.hera.hub.model;

public class Host
{
  private int id;
  private String name;
  private String display;
  private int devicesCount;
  private boolean active = true;

  public Host()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getId()
  {
    return id;
  }

  public String getDisplay()
  {
    return display;
  }

  public int getDevicesCount()
  {
    return devicesCount;
  }

  public void setDevicesCount(int devicesCount)
  {
    this.devicesCount = devicesCount;
  }

  public void setActive(boolean active)
  {
    this.active = active;
  }

  public boolean isActive()
  {
    return active;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
