package sim.model.ride;

import java.time.LocalDateTime;
import sim.model.customer.Customer;
import sim.model.driver.Driver;
import sim.model.type.RideType;

/**
 * Concrete StandardRide class, representing a ride of type "standard".
 */
public final class StandardRide extends Ride {

  /**
   * Constructor for option1.model.ride.StandardRide class.
   *
   * @param customer      the customer who requested the ride
   * @param driver        the driver assigned to the ride
   * @param requestTime   the time when the ride was requested
   * @param departureTime the time when the ride started
   * @param arrivalTime   the time when the ride ended
   * @param rideDistance  the distance covered by the ride in miles
   * @param rideLength    the duration of the ride in seconds
   */
  public StandardRide(Customer customer, Driver driver,
      LocalDateTime requestTime, LocalDateTime departureTime, LocalDateTime arrivalTime,
      Double rideDistance, Long rideLength) {
    super(customer, driver, requestTime, departureTime, arrivalTime, rideDistance,
        RideType.STANDARD_PICK_UP.getPriority(), rideLength);
  }

  @Override
  public String toString() {
    return "StandardRide{} " + super.toString();
  }
}
