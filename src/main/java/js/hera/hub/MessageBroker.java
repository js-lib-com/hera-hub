package js.hera.hub;

import com.jslib.container.sse.EventStream;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Remote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;

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
