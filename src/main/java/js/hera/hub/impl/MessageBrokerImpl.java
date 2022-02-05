package js.hera.hub.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import com.jslib.automata.Automata;

import jakarta.inject.Inject;
import js.hera.hub.Application;
import js.hera.hub.DeviceState;
import js.hera.hub.Message;
import js.hera.hub.MessageBroker;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.contextparam.ContextParam;
import js.tiny.container.net.EventStream;

class MessageBrokerImpl implements MessageBroker
{
  private static final Log log = LogFactory.getLog(MessageBrokerImpl.class);

  @ContextParam(name = "influx.url", mandatory = false)
  private static String INFLUX_URL;

  private final Application application;
  private final Automata automata;
  private final InfluxDB influx;
  private final List<EventStream> streams = Collections.synchronizedList(new ArrayList<EventStream>());

  @Inject
  public MessageBrokerImpl(Application application)
  {
    log.trace("MessageBrokerImpl(Application)");
    this.application = application;
    this.automata = application.getAutomata();

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

      // handle automata event
      Map<String, String> event = new HashMap<>();
      event.put("deviceName", deviceState.getDeviceName());
      event.put("value", Double.toString(deviceState.getValue()));
      automata.handleEvent(event);

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
