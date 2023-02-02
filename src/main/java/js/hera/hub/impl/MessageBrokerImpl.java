package js.hera.hub.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.container.contextparam.ContextParam;
import com.jslib.container.sse.EventStream;

import jakarta.inject.Inject;
import js.hera.hub.Application;
import js.hera.hub.DeviceState;
import js.hera.hub.Message;
import js.hera.hub.MessageBroker;

class MessageBrokerImpl implements MessageBroker
{
  private static final Log log = LogFactory.getLog(MessageBrokerImpl.class);

  @ContextParam(name = "influx.url", mandatory = false)
  private static String INFLUX_URL;

  private final Application application;
  private final InfluxDB influx;
  private final List<EventStream> streams = Collections.synchronizedList(new ArrayList<EventStream>());

  @Inject
  public MessageBrokerImpl(Application application)
  {
    log.trace("MessageBrokerImpl(Application)");
    this.application = application;

    if(INFLUX_URL != null) {
      this.influx = InfluxDBFactory.connect(INFLUX_URL);
      this.influx.setDatabase("hera");
    }
    else {
      this.influx = null;
    }
  }

  @Override
  public void bindStream(EventStream stream)
  {
    log.trace("bindStream(EventStream)");
    streams.add(stream);
  }

  @Override
  public void unbindStream(EventStream stream)
  {
    log.trace("unbindStream(EventStream)");
    streams.remove(stream);
  }

  @Override
  public void publish(Message message)
  {
    log.trace("publish(Message)");

    // {"deviceName":"thermostat-sensor","temperature":"23.45"}
    // {"deviceName":"bathroom-dht","humidity":"62","temperature":"23.45"}

    switch(message.getType()) {
    case DEVICE_STATE:
      final DeviceState deviceState = message.value();
      log.debug("Device state: device name |%s|, value |%f|.", deviceState.getDeviceName(), deviceState.getValue());

      if(influx != null) {
        Point point = Point.measurement(deviceState.getDeviceName()).addField("value", deviceState.getValue()).build();
        influx.write(point);
      }

      // special treatment, aka hack, for power meter
      if(deviceState.getDeviceName().equals("power-meter")) {
        application.computePowerValue(deviceState.getValue());
      }

      // push device state to UI via server sent event stream
      for(EventStream stream : streams) {
        stream.push(deviceState);
      }
      break;

    case LOG_RECORD:
      // TODO: deprecated
      break;
    }
  }
}
