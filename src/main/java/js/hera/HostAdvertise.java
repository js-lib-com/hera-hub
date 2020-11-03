package js.hera;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HostAdvertise
{
  /** Name of the host system. */
  private String hostName;
  /** The URL of the host system where device instances are deployed. */
  private URL hostURL;
  /** Device instances deployed on target host. */
  private List<DeviceAdvertise> devices = new ArrayList<DeviceAdvertise>();

  public void setHostURL(URL hostURL)
  {
    this.hostURL = hostURL;
  }

  public URL getHostURL()
  {
    return hostURL;
  }

  public String getHostName()
  {
    return hostName;
  }

  public void setHostName(String hostName)
  {
    this.hostName = hostName;
  }

  public void addDeviceAdvertise(DeviceAdvertise device)
  {
    this.devices.add(device);
  }

  public List<DeviceAdvertise> getDevices()
  {
    return devices;
  }
}
