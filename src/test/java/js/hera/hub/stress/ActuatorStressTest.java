package js.hera.hub.stress;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class ActuatorStressTest
{
  @Test
  public void invoke() throws Exception
  {
    final FileWriter csv = new FileWriter("d://tmp/hera/hera.csv");

    for(int i = 0; i < 10000; ++i) {
      System.out.println(i);
      long startTimestamp = System.currentTimeMillis();

      URL url = new URL("http://192.168.0.5/js/hera/dev/HostSystem/invoke.rmi");
      String parameters = "[\"actuator1\",\"toggle\",\"" + i + "\"]";

      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setConnectTimeout(4000);
      connection.setReadTimeout(4000);
      connection.setRequestProperty("Content-Length", Integer.toString(parameters.length()));

      OutputStream output = connection.getOutputStream();
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
      writer.append(parameters).flush();

      assertThat(connection.getResponseCode(), equalTo(204));
      long elapsedTime = System.currentTimeMillis() - startTimestamp;
      assertThat(elapsedTime, lessThan(1500L));
      
      csv.append(String.format("%d,%d\r\n", i,elapsedTime));
    }

    csv.close();
  }
}