package sim.control.factory;

import java.time.LocalDateTime;
import sim.model.ride.Ride;

/**
 * Interface for RideFactory.
 */
public interface RideGenerator {

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
  Ride generateRide(
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
      Long rideLength);
}
