package js.hera.hub.model;

@SuppressWarnings("unused")
public class SelectItem
{
  private String value;
  private String text;

  public SelectItem()
  {
  }

  public SelectItem(Zone zone)
  {
    this.value = Integer.toString(zone.getId());
    this.text = zone.getDisplay();
  }

  public SelectItem(DeviceCategory category)
  {
    this.value = Integer.toString(category.getId());
    this.text = category.getDisplay();
  }

  public SelectItem(Host host)
  {
    this.value = Integer.toString(host.getId());
    this.text = host.getDisplay();
  }
}
