package sim.model.ride;

import java.time.LocalDateTime;
import sim.model.customer.Customer;
import sim.model.driver.Driver;
import sim.model.type.RideType;

/**
 * Concrete EnvironmentallyConsciousRide class, representing a ride of type "environmentally
 * conscious".
 */
public final class EnvironmentallyConsciousRide extends Ride {

  /**
   * Constructor for option1.model.ride.EnvironmentallyConsciousRide class.
   *
   * @param customer      the customer who requested the ride
   * @param driver        the driver assigned to the ride
   * @param requestTime   the time when the ride was requested
   * @param departureTime the time when the ride started
   * @param arrivalTime   the time when the ride ended
   * @param rideDistance  the distance covered by the ride in miles
   * @param rideLength    the duration of the ride in seconds
   */
  public EnvironmentallyConsciousRide(Customer customer, Driver driver, LocalDateTime requestTime,
      LocalDateTime departureTime, LocalDateTime arrivalTime, Double rideDistance,
      Long rideLength) {
    super(customer, driver, requestTime, departureTime, arrivalTime, rideDistance,
        RideType.ENVIRONMENTALLY_CONSCIOUS_PICK_UP.getPriority(), rideLength);
  }

  @Override
  public String toString() {
    return "EnvironmentallyConsciousRide{} " + super.toString();
  }
}
