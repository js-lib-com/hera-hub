package js.hera.hub.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import js.hera.auto.engine.Automata;
import js.hera.hub.Application;
import js.hera.hub.DeviceState;
import js.hera.hub.Message;
import js.hera.hub.MessageBroker;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.core.App;
import js.tiny.container.core.AppContext;
import js.tiny.container.net.EventStream;

class MessageBrokerImpl implements MessageBroker
{
  private static final Log log = LogFactory.getLog(MessageBrokerImpl.class);

  private final Automata automata;
  private final List<EventStream> streams = Collections.synchronizedList(new ArrayList<EventStream>());

  public MessageBrokerImpl(AppContext context)
  {
    log.trace("EventBrokerImpl(AppContext)");
    this.automata = ((Application)context.getInstance(App.class)).getAutomata();
  }

  void bindStream(EventStream stream)
  {
    log.trace("bindStream(EventStream)");
    streams.add(stream);
  }

  void unbindStream(EventStream stream)
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

      // handle automata event
      Map<String, String> event = new HashMap<>();
      event.put("deviceName", deviceState.getDeviceName());
      event.put("value", Double.toString(deviceState.getValue()));
      automata.handleEvent(event);

      // special treatment, aka hack, for power meter
      if(deviceState.getDeviceName().equals("power-meter")) {
        Application.instance().computePowerValue(deviceState.getValue());
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
