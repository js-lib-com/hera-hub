package js.hera.hub;

import org.influxdb.InfluxDB.LogLevel;

public class LogRecord
{
  private LogLevel level;
  private String message;

  public LogLevel getLevel()
  {
    return level;
  }

  public String getMessage()
  {
    return message;
  }
}
