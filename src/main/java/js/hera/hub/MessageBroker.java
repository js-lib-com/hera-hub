package js.hera.hub;

import javax.annotation.ManagedBean;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ws.rs.Path;

@ManagedBean
@PermitAll
@Path("broker")
@Remote
public interface MessageBroker
{
  void publish(Message message);
}
