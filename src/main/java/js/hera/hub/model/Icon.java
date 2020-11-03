package js.hera.hub.model;

public class Icon
{
  private final int id;
  private final String name;
  private final String picture;

  public Icon()
  {
    this.id = 0;
    this.name = null;
    this.picture = null;
  }

  public Icon(int id, String name, String picture)
  {
    this.id = id;
    this.name = name;
    this.picture = picture;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getPicture()
  {
    return picture;
  }
}
