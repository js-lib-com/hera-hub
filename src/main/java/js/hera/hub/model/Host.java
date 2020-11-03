package js.hera.hub.model;

import java.net.URL;

import js.hera.HostAdvertise;

public class Host
{
  private int id;
  private String name;
  private URL url;

  public Host()
  {
  }

  public Host(HostAdvertise hostAdvertise)
  {
    name = hostAdvertise.getHostName();
    url = hostAdvertise.getHostURL();
  }

  public void update(HostAdvertise hostAdvertise)
  {
    url = hostAdvertise.getHostURL();
  }

  public URL getURL()
  {
    return url;
  }

  public void setURL(URL url)
  {
    this.url = url;
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

  @Override
  public String toString()
  {
    return name;
  }
}
