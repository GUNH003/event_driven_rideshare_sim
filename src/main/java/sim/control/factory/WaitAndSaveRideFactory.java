package sim.control.factory;

import java.time.LocalDateTime;
import sim.model.customer.BasicCustomer;
import sim.model.customer.Customer;
import sim.model.driver.BasicDriver;
import sim.model.driver.Driver;
import sim.model.ride.Ride;
import sim.model.ride.WaitAndSaveRide;

/**
 * WaitAndSaveRideFactory class, representing a factory that produces all the products needed to
 * build a WaitAndSaveRide.
 */
public final class WaitAndSaveRideFactory extends RideFactory {

  /**
   * Constructor for option1.control.factory.WaitAndSaveRideFactory class.
   */
  public WaitAndSaveRideFactory() {
  }

  /**
   * Creates a BasicDriver object.
   *
   * @param driverName         the name of the driver
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   * @return a BasicDriver object
   */
  @Override
  protected Driver createDriver(String driverName, Integer numOfRidesFinished, Double driverSpeed) {
    return new BasicDriver(driverName, numOfRidesFinished, driverSpeed);
  }

  /**
   * Creates a BasicCustomer object.
   *
   * @param customerName     the name of the customer
   * @param startingLocation the starting location of the customer
   * @param desiredLocation  the desired location of the customer
   * @return a BasicCustomer object
   */
  @Override
  protected Customer createCustomer(String customerName, String startingLocation,
      String desiredLocation) {
    return new BasicCustomer(customerName, startingLocation, desiredLocation);
  }

  /**
   * Creates a WaitAndSaveRide object.
   *
   * @param customer      the customer who requested the ride
   * @param driver        the driver assigned to the ride
   * @param requestTime   the time when the ride was requested
   * @param departureTime the time when the ride started
   * @param arrivalTime   the time when the ride ended
   * @param rideDistance  the distance covered by the ride in miles
   * @param rideLength    the duration of the ride in seconds
   * @return a WaitAndSaveRide object
   */
  @Override
  protected Ride createRide(Customer customer, Driver driver, LocalDateTime requestTime,
      LocalDateTime departureTime, LocalDateTime arrivalTime, Double rideDistance,
      Long rideLength) {
    return new WaitAndSaveRide(customer, driver, requestTime, departureTime, arrivalTime,
        rideDistance, rideLength);
  }

  @Override
  public String toString() {
    return "WaitAndSaveRideFactory{} " + super.toString();
  }
}
