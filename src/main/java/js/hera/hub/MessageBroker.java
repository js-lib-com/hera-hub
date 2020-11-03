package js.hera.hub;

import js.tiny.container.annotation.Service;
import js.tiny.container.annotation.Public;

@Service("broker")
@Public
public interface MessageBroker
{
  void publish(Message message);
}
