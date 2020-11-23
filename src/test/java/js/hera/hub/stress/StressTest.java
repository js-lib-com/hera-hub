package js.hera.hub.stress;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import js.util.Strings;

public abstract class StressTest
{
  protected int getTestsCount()
  {
    return 1000;
  }

  protected void onResult(String result)
  {

  }

  protected void invoke(String host, String action, String method, Object... parameter) throws Exception
  {
    final FileWriter csv = new FileWriter("d://tmp/hera/hera.csv");

    for(int i = 0; i < getTestsCount(); ++i) {
      System.out.println(i);
      long startTimestamp = System.currentTimeMillis();

      URL url = new URL(String.format("http://%s.local/js/hera/dev/HostSystem/invoke.rmi", host));
      String parameters = String.format("[\"%s\",\"%s\"]", action, method);

      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setConnectTimeout(8000);
      connection.setReadTimeout(8000);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Content-Length", Integer.toString(parameters.length()));

      OutputStream output = connection.getOutputStream();
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
      writer.append(parameters).flush();

      assertThat(connection.getResponseCode(), equalTo(200));
      long elapsedTime = System.currentTimeMillis() - startTimestamp;
      assertThat(elapsedTime, lessThan(8000L));

      csv.append(String.format("%d,%d\r\n", i, elapsedTime));
      
      onResult(Strings.load(connection.getInputStream()));
      connection.disconnect();
    }

    csv.close();
  }
}