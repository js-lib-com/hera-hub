package js.hera.hub;

import com.jslib.api.json.Json;
import com.jslib.util.Classes;

public class Message
{
  private Type type;
  private String value;

  public Type getType()
  {
    return type;
  }

  public <T> T value()
  {
    Json json = Classes.loadService(Json.class);
    return json.parse(value, type.value());
  }

  public static enum Type
  {
    DEVICE_STATE(DeviceState.class), LOG_RECORD(LogRecord.class);

    private Class<?> type;

    private Type(Class<?> type)
    {
      this.type = type;
    }

    public Class<?> value()
    {
      return type;
    }
  }
}
