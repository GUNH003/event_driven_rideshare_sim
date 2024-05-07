package sim.model.type;

/**
 * Enum representing different types of rides.
 */
public enum RideType {
  EXPRESS_PICK_UP(0),                      // Express pick up ride, highest priority
  STANDARD_PICK_UP(1),                     // Standard pick up ride
  WAIT_AND_SAVE_PICK_UP(2),                // Wait and save pick up ride
  ENVIRONMENTALLY_CONSCIOUS_PICK_UP(3);    // Environmentally conscious pick up ride

  private final Integer priority;

  /**
   * Constructor for option1.model.type.RideType class. It contains priority as field.
   *
   * @param priority the priority of the ride type
   */
  RideType(Integer priority) {
    this.priority = priority;
  }

  /**
   * Gets the priority of the ride type.
   *
   * @return the priority of the ride type.
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Gets the ride type based on the given priority.
   *
   * @param priority the given priority
   * @return the ride type
   */
  public static String getRideType(Integer priority) {
    for (RideType type : RideType.values()) {
      if (type.getPriority().equals(priority)) {
        return type.toString();
      }
    }
    return "Unknown Ride Type";
  }
}
