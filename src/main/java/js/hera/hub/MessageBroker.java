package js.hera.hub;

import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ws.rs.Path;

import jakarta.enterprise.context.ApplicationScoped;
import js.tiny.container.net.EventStream;

@ApplicationScoped
@Remote
@Path("broker")
@PermitAll
public interface MessageBroker
{

  void bindStream(EventStream stream);

  void unbindStream(EventStream stream);

  void publish(Message message);

}
