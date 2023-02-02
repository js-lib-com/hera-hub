package js.hera.hub.stress;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Test;

import com.jslib.api.json.Json;
import com.jslib.util.Classes;

import js.hera.dev.DHTSensor;

public class DHTSensorStressTest extends StressTest
{
  private Json json;

  @Before
  public void beforeTest()
  {
    json = Classes.loadService(Json.class);
  }

  @Test
  public void invoke() throws Exception
  {
    invoke("dht-sensor", "dht-sensor", "getValue");
  }

  protected int getTestsCount()
  {
    return 10;
  }

  protected void onResult(String result)
  {
    System.out.println(result);
    DHTSensor.Value value = json.parse(result, DHTSensor.Value.class);
    assertThat(value.getHumidity(), not(equalTo(0.0F)));
    assertThat(value.getTemperature(), not(equalTo(0.0F)));
  }
}
