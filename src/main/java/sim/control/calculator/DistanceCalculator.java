package sim.control.calculator;

import java.util.Random;

/**
 * DistanceCalculator class, generates random distance data used in the simulation.
 */
public final class DistanceCalculator {

  private static final double OFF_SET = 1.0;

  /**
   * Constructor for option1.calculator.DistanceCalculator class.
   */
  public DistanceCalculator() {
  }

  /**
   * Generates distance based between 1 and the upper bound defined by the user.
   *
   * @param upperBound the distance generation upper bound defined by the user
   * @return the generated distance
   */
  public Double calculateDistance(Double upperBound) {
    if (!validateUpperBound(upperBound)) {
      throw new IllegalArgumentException("Upper bound must be a positive number.");
    }
    Random random = new Random();
    return random.nextDouble(upperBound) + OFF_SET;
  }

  /**
   * Helper method. Validates the upper bound argument.
   *
   * @param upperBound the upper bound argument
   * @return true if the upper bound argument is greater than zero, false otherwise
   */
  private Boolean validateUpperBound(Double upperBound) {
    return upperBound > 0;
  }
}
