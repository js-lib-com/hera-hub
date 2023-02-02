package js.hera.hub.impl;

import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.container.sse.EventStream;

import jakarta.inject.Inject;
import js.hera.hub.MessageBroker;

/**
 * Event stream specialized on push notifications.
 * 
 * @author Iulian Rotaru
 */
final class DeviceEventStream extends EventStream
{
  private static final Log log = LogFactory.getLog(DeviceEventStream.class);

  private MessageBroker messageBroker;

  @Inject
  public DeviceEventStream(MessageBroker messageBroker)
  {
    log.trace("DeviceEventStream(MessageBroker)");
    this.messageBroker = messageBroker;
  }

  @Override
  public void onOpen()
  {
    messageBroker.bindStream(this);
    super.onOpen();
    log.debug("Open push notification stream with device |%s|.", "test");
  }

  @Override
  public void onClose()
  {
    log.debug("Close push notification stream with device |%s|.", "test");
    messageBroker.unbindStream(this);
    super.onClose();
  }
}
