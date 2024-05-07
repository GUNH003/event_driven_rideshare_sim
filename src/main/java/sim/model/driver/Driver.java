package sim.model.driver;

import java.util.Objects;

/**
 * Abstract Driver class, representing a driver.
 */
public abstract class Driver {

  private static final int INITIAL_NUM_OF_RIDES_COMPLETED = 0;

  protected final String driverName;
  protected final Integer numOfRidesFinished;
  protected final Double driverSpeed;

  /**
   * Constructor for option1.model.driver.Driver class.
   *
   * @param driverName         the name of the driver
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   */
  public Driver(String driverName, Integer numOfRidesFinished, Double driverSpeed) {
    this.driverName = driverName;
    this.numOfRidesFinished = numOfRidesFinished;
    this.driverSpeed = driverSpeed;
  }

  /**
   * Constructor for option1.model.driver.Driver class. The number of rides finished by the driver
   * is initialized to 0.
   *
   * @param driverName  the name of the driver
   * @param driverSpeed the driving speed of the driver
   */
  public Driver(String driverName, Double driverSpeed) {
    this.driverName = driverName;
    this.numOfRidesFinished = INITIAL_NUM_OF_RIDES_COMPLETED;
    this.driverSpeed = driverSpeed;
  }

  /**
   * Gets the name of the driver.
   *
   * @return the name of the driver
   */
  public String getDriverName() {
    return driverName;
  }

  /**
   * Gets the number of rides finished by the driver.
   *
   * @return the number of rides finished by the driver
   */
  public Integer getNumOfRidesFinished() {
    return numOfRidesFinished;
  }

  /**
   * Gets the driving speed of the driver.
   *
   * @return the driving speed of the driver
   */
  public Double getDriverSpeed() {
    return driverSpeed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Driver driver = (Driver) o;
    return Objects.equals(getDriverName(), driver.getDriverName())
        && Objects.equals(getNumOfRidesFinished(), driver.getNumOfRidesFinished())
        && Objects.equals(getDriverSpeed(), driver.getDriverSpeed());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDriverName(), getNumOfRidesFinished(), getDriverSpeed());
  }

  @Override
  public String toString() {
    return "Driver{" +
        "driverName='" + driverName + '\'' +
        ", numOfRidesFinished=" + numOfRidesFinished +
        ", driverSpeed=" + driverSpeed +
        '}';
  }
}

