package sim.model.driver;

/**
 * Basic Driver class, representing a basic driver.
 */
public final class BasicDriver extends Driver {

  /**
   * Constructor for option1.model.driver.BasicDriver class.
   *
   * @param driverName         the name of the driver
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   */
  public BasicDriver(String driverName, Integer numOfRidesFinished, Double driverSpeed) {
    super(driverName, numOfRidesFinished, driverSpeed);
  }

  /**
   * Constructor for option1.model.driver.BasicDriver class. The number of rides finished by the
   * driver is initialized to 0.
   *
   * @param driverName  the name of the driver
   * @param driverSpeed the driving speed of the driver
   */
  public BasicDriver(String driverName, Double driverSpeed) {
    super(driverName, driverSpeed);
  }

  @Override
  public String toString() {
    return "BasicDriver{} " + super.toString();
  }
}
