package sim.control.calculator;

import java.time.LocalDateTime;

/**
 * ArrivalTimeCalculator class, calculates arrival time for a ride.
 */
public final class ArrivalTimeCalculator {

  private static final int MINUTES_IN_HOUR = 60;
  private static final int SECONDS_IN_MIN = 60;

  /**
   * Constructor of option1.calculator.ArrivalTimeCalculator class.
   */
  public ArrivalTimeCalculator() {
  }

  /**
   * Calculates the arrival time of a ride, given the ride's distance, speed and departure time.
   *
   * @param distance      the distance covered by the ride in miles
   * @param speed         the driving speed of the driver assigned to the ride
   * @param departureTime the departure time of the ride
   * @return the arrival time
   */
  public LocalDateTime calculate(Double distance, Double speed, LocalDateTime departureTime) {
    if (!validateDistance(distance) || !validateSpeed(speed)) {
      throw new IllegalArgumentException("Distance and speed must be positive numbers.");
    }
    double hours = distance / speed;
    long seconds = Math.round(hours * MINUTES_IN_HOUR * SECONDS_IN_MIN);
    return departureTime.plusSeconds(seconds);
  }

  /**
   * Helper method. Validates the distance argument.
   *
   * @param distance the distance covered by the ride in miles
   * @return true if the distance is greater than zero, false otherwise
   */
  private Boolean validateDistance(Double distance) {
    return distance > 0;
  }

  /**
   * Helper method. Validates the speed argument.
   *
   * @param speed the driving speed of the driver assigned to the ride
   * @return true if the speed is greater than zero, false otherwise
   */
  private Boolean validateSpeed(Double speed) {
    return speed > 0;
  }
}
