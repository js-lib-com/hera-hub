package js.hera.hub.stress;

import org.junit.Test;

public class ActuatorStressTest extends StressTest
{
  protected int getTestsCount()
  {
    return 10000;
  }

  @Test
  public void invoke() throws Exception
  {
    invoke("dht-sensor", "actuator-6", "toggle");
  }
}