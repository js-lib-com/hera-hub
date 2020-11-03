package js.hera.hub.impl;

import js.hera.hub.MessageBroker;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.core.Factory;
import js.tiny.container.net.EventStream;

/**
 * Event stream specialized on push notifications.
 * 
 * @author Iulian Rotaru
 */
final class DeviceEventStream extends EventStream
{
  /** Class logger. */
  private static final Log log = LogFactory.getLog(DeviceEventStream.class);

  /** Event broker service. */
  private MessageBrokerImpl service;

  /**
   * Private constructor to avoid instantiation with <code>new</code> operator.
   */
  private DeviceEventStream()
  {
    log.trace("DeviceEventStream()");
    this.service = (MessageBrokerImpl)Factory.getInstance(MessageBroker.class);
  }

  @Override
  public void onOpen()
  {
    service.bindStream(this);
    super.onOpen();
    log.debug("Open push notification stream with device |%s|.", "test");
  }

  @Override
  public void onClose()
  {
    log.debug("Close push notification stream with device |%s|.", "test");
    service.unbindStream(this);
    super.onClose();
  }
}
