package js.hera.hub.unit.it;

import org.junit.Ignore;

import js.hera.hub.client.DeviceFactory;
import js.hera.hub.client.Service;
import js.hera.hub.client.SetpointThermostat;
import js.lang.Callback;
import js.tiny.container.spi.Factory;
import junit.framework.TestCase;

@Ignore
public class WebServiceUnitTest extends TestCase
{
  @Override
  protected void setUp() throws Exception
  {
    //TestContext.start(new File("test/js/hera/hub/test-config.xml"));
  }

  public void testMultipleSetters() throws InterruptedException
  {
    Service hera = Factory.getInstance(Service.class);

    // hera.forceSynchronousMode(true);

    for(int i = 0; i < 100; ++i) {
      // hera.invokeDeviceAction("heating-system", "setTemperature", i, 23.4, Transaction.SYNC_MODE);
      hera.invokeDeviceAction("heating-system", "setTemperature", i, 23.4);
    }

    Thread.sleep(1000);
  }

  public void testMultipleGetters() throws InterruptedException
  {
    Service hera = Factory.getInstance(Service.class);
    for(int i = 0; i < 100; ++i) {
      hera.invokeDeviceAction("heating-system", "getTemperature", new Callback<Double>()
      {
        @Override
        public void handle(Double value)
        {
          System.out.println(value);
        }
      }, i);

      hera.invokeDeviceAction("heating-system", "setSetpoint", new Callback<Double>()
      {
        @Override
        public void handle(Double value)
        {
          System.out.println(value);
        }
      }, i);
    }

    Thread.sleep(1000);
  }

  public void testInvokeDeviceAction() throws InterruptedException
  {
    SetpointThermostat thermostat = DeviceFactory.createDeviceProxy("http://localhost/hera-hub", "heating-system", SetpointThermostat.class);
    thermostat.getSetpoint(new Callback<Double>()
    {
      @Override
      public void handle(Double value)
      {
        System.out.println(value);
      }
    });

    Thread.sleep(1000);
  }
}
