package js.hera.hub;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import com.jslib.automata.Automata;
import com.jslib.automata.AutomataImpl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Priority;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import js.tiny.container.contextparam.ContextParam;
import js.util.Strings;

@ApplicationScoped
@Startup
@Priority(0)
public class Application
{
  @ContextParam(name = "files.store", mandatory = true)
  private static File FILES_STORE;

  private final Automata automata;
  private final File automataSourceDir;

  private final String name;
  private double power;

  @Inject
  public Application(ServletContext servletContext) throws IOException, ClassNotFoundException
  {
    this.automata = new AutomataImpl(getAppFile("auto"));
    this.automataSourceDir = getAppFile("auto/src");

    // if present, context path always starts with path separator
    final String contextPath = servletContext.getContextPath();
    this.name = contextPath.isEmpty() ? null : contextPath.substring(1);
  }

  @PostConstruct
  public void postConstruct()
  {
    try {
      automata.postConstruct();
    }
    catch(Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @PreDestroy
  public void preDestroy()
  {
    try {
      automata.preDestroy();
    }
    catch(Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String getAppName()
  {
    return name;
  }

  public File getAppFile(String path)
  {
    return new File(FILES_STORE, path);
  }

  public Automata getAutomata()
  {
    return automata;
  }

  public File getAutomataSourceDir()
  {
    return automataSourceDir;
  }

  public void computePowerValue(double power)
  {
    File energyIndex = getAppFile("energy-index");
    try {
      double index = Double.parseDouble(Strings.load(energyIndex));
      index += 0.001;
      Strings.save(Double.toString(index), energyIndex);
    }
    catch(NumberFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch(IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.power = power;
  }

  public double getPowerValue()
  {
    return power;
  }
}
