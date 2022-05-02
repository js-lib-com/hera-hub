package js.hera.hub;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import jakarta.annotation.Priority;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.contextparam.ContextParam;
import js.util.Strings;

@ApplicationScoped
@Startup
@Priority(0)
public class Application
{
  private static final Log log = LogFactory.getLog(Application.class);

  @ContextParam(name = "files.store", mandatory = true)
  private static File FILES_STORE;

  private final String name;
  private double power;

  @Inject
  public Application(ServletContext servletContext) throws IOException, ClassNotFoundException
  {
    log.trace("Application(ServletContext)");

    // if present, context path always starts with path separator
    final String contextPath = servletContext.getContextPath();
    this.name = contextPath.isEmpty() ? null : contextPath.substring(1);
  }

  public String getAppName()
  {
    return name;
  }

  public File getAppFile(String path)
  {
    return new File(FILES_STORE, path);
  }

  public void computePowerValue(double power)
  {
    File energyIndex = getAppFile("energy-index");
    try {
      double index = Double.parseDouble(Strings.load(energyIndex));
      index += 0.001;
      Strings.save(Double.toString(index), energyIndex);
    }
    catch(Exception e) {
      log.error(e);
    }

    this.power = power;
  }

  public double getPowerValue()
  {
    return power;
  }
}
