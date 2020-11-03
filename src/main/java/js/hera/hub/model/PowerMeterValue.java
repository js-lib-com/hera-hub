package js.hera.hub.model;

public class PowerMeterValue
{
  private final double energy;
  private final double power;

  public PowerMeterValue(double energy, double power)
  {
    this.energy = Math.round(energy * 100) / 100.0;
    this.power = Math.round(power);
  }

  public double getEnergy()
  {
    return energy;
  }

  public double getPower()
  {
    return power;
  }
}
