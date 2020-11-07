package js.hera.hub;

import java.io.File;
import java.io.IOException;

import js.hera.auto.engine.Automata;
import js.hera.auto.engine.AutomataImpl;
import js.tiny.container.core.App;
import js.tiny.container.core.AppContext;
import js.util.Strings;

public class Application extends App
{
  private static Application instance;

  public static Application instance()
  {
    return instance;
  }

  private final AppContext context;
  private final Automata automata;
  private final File automataSourceDir;

  private double power;

  public Application(AppContext context) throws IOException, ClassNotFoundException
  {
    super(context);
    instance = this;
    this.context = context;
    this.automata = new AutomataImpl(context.getAppFile("auto"));
    this.automataSourceDir = context.getAppFile("auto/src");
  }

  @Override
  public void postConstruct() throws Exception
  {
    super.postConstruct();
    automata.postConstruct();
    Service service = context.getInstance(Service.class);
    this.automata.setDeviceActionHandler(service);
  }

  @Override
  public void preDestroy() throws Exception
  {
    automata.preDestroy();
    super.preDestroy();
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
    File energyIndex = context.getAppFile("energy-index");
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
