package sim.control.factory;

import java.time.LocalDateTime;
import sim.model.customer.Customer;
import sim.model.driver.Driver;
import sim.model.ride.Ride;

/**
 * Abstract RideFactory class, representing a factory that produces all the products needed to build
 * a Ride.
 */
public abstract class RideFactory implements RideGenerator {

  /**
   * Constructor for option1.control.factory.RideFactory class.
   */
  public RideFactory() {
  }

  /**
   * Creates a Driver object.
   *
   * @param driverName         the name of the driver
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   * @return a Driver object
   */
  protected abstract Driver createDriver(String driverName, Integer numOfRidesFinished,
      Double driverSpeed);

  /**
   * Creates a Customer object.
   *
   * @param customerName     the name of the customer
   * @param startingLocation the starting location of the customer
   * @param desiredLocation  the desired location of the customer
   * @return a Customer object
   */
  protected abstract Customer createCustomer(String customerName, String startingLocation,
      String desiredLocation);

  /**
   * Creates a Ride object.
   *
   * @param customer      the customer who requested the ride
   * @param driver        the driver assigned to the ride
   * @param requestTime   the time when the ride was requested
   * @param departureTime the time when the ride started
   * @param arrivalTime   the time when the ride ended
   * @param rideDistance  the distance covered by the ride in miles
   * @param rideLength    the duration of the ride in seconds
   * @return a Ride object
   */
  protected abstract Ride createRide(Customer customer, Driver driver, LocalDateTime requestTime,
      LocalDateTime departureTime, LocalDateTime arrivalTime, Double rideDistance,
      Long rideLength);

  /**
   * Generates a Ride objects with the given arguments.
   *
   * @param driverName         the name of the driver
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   * @param customerName       the name of the customer
   * @param startingLocation   the starting location of the customer
   * @param desiredLocation    the desired location of the customer
   * @param requestTime        the time when the ride was requested
   * @param departureTime      the time when the ride started
   * @param arrivalTime        the time when the ride ended
   * @param rideDistance       the distance covered by the ride in miles
   * @param rideLength         the duration of the ride in seconds
   * @return a Ride object
   */
  @Override
  public Ride generateRide(
      String driverName,
      Integer numOfRidesFinished,
      Double driverSpeed,
      String customerName,
      String startingLocation,
      String desiredLocation,
      LocalDateTime requestTime,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      Double rideDistance,
      Long rideLength) {
    Driver driver = createDriver(driverName, numOfRidesFinished, driverSpeed);
    Customer customer = createCustomer(customerName, startingLocation, desiredLocation);
    return createRide(customer, driver, requestTime, departureTime, arrivalTime, rideDistance,
        rideLength);
  }

  @Override
  public String toString() {
    return "RideFactory{}";
  }
}
