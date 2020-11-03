package js.hera.hub;

import js.json.Json;
import js.tiny.container.core.Factory;

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
    Json json = Factory.getInstance(Json.class);
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
