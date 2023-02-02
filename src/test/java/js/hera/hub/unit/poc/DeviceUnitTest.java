package js.hera.hub.unit.poc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.function.Consumer;

import org.junit.Ignore;

import com.jslib.net.client.HttpRmiTransaction;

import js.hera.dev.DHTSensor;
import junit.framework.TestCase;

@Ignore
public class DeviceUnitTest extends TestCase
{
  public void testDHTGetValue() throws Throwable
  {
    final FileWriter writer = new FileWriter("d://tmp/dht.log");
    for(int i = 0; i < 1000; ++i) {
      System.out.println(i);
      HttpRmiTransaction rmi = HttpRmiTransaction.getInstance("http://192.168.1.11");
      rmi.setMethod("js.hera.dev.HostSystem", "invoke");
      rmi.setReturnType(DHTSensor.Value.class);
      rmi.setArguments("dht", "getValue");
      rmi.exec(new Consumer<DHTSensor.Value>()
      {
        @Override
        public void accept(DHTSensor.Value value)
        {
          System.out.printf("Humidity:%.02f Temperature:%.02f\r\n", value.getHumidity(), value.getTemperature());
          try {
            writer.write(String.format("%s -- Humidity:%.02f Temperature:%.02f\r\n", new Date(), value.getHumidity(), value.getTemperature()));
            writer.flush();
          }
          catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      });
      Thread.sleep(4000);
    }
    writer.close();
  }
}
