package sim.model.ride;

import java.time.LocalDateTime;
import java.util.Objects;
import sim.model.customer.Customer;
import sim.model.driver.Driver;

/**
 * Abstract Ride class, representing a ride.
 */
public abstract class Ride {

  protected final Customer customer;
  protected final Driver driver;
  protected final LocalDateTime requestTime;
  protected final LocalDateTime departureTime;
  protected final LocalDateTime arrivalTime;
  protected final Double rideDistance;
  protected final Integer priority;
  protected final Long rideLength;

  /**
   * Constructor for option1.model.ride.Ride class.
   *
   * @param customer      the customer who requested the ride
   * @param driver        the driver assigned to the ride
   * @param requestTime   the time when the ride was requested
   * @param departureTime the time when the ride started
   * @param arrivalTime   the time when the ride ended
   * @param rideDistance  the distance covered by the ride in miles
   * @param priority      the priority level of the ride, with lower numbers indicating higher
   *                      priority
   * @param rideLength    the duration of the ride in seconds
   */
  public Ride(Customer customer, Driver driver, LocalDateTime requestTime,
      LocalDateTime departureTime, LocalDateTime arrivalTime, Double rideDistance, Integer priority,
      Long rideLength) {
    this.customer = customer;
    this.driver = driver;
    this.requestTime = requestTime;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.rideDistance = rideDistance;
    this.priority = priority;
    this.rideLength = rideLength;
  }

  /**
   * Gets the customer.
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Gets the driver.
   *
   * @return the driver.
   */
  public Driver getDriver() {
    return driver;
  }

  /**
   * Gets the time when the ride was requested.
   *
   * @return the time when the ride was requested
   */
  public LocalDateTime getRequestTime() {
    return requestTime;
  }

  /**
   * Gets the time when the ride started.
   *
   * @return the time when the ride started
   */
  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Gets the time when the ride ended.
   *
   * @return the time when the ride ended
   */
  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  /**
   * Gets the distance covered by the ride in miles.
   *
   * @return the distance covered by the ride in miles
   */
  public Double getRideDistance() {
    return rideDistance;
  }

  /**
   * Gets the priority level of the ride.
   *
   * @return the priority level of the ride
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Gets the duration of the ride.
   *
   * @return the duration of the ride
   */
  public Long getRideLength() {
    return rideLength;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ride ride = (Ride) o;
    return Objects.equals(getCustomer(), ride.getCustomer())
        && Objects.equals(getDriver(), ride.getDriver())
        && Objects.equals(getRequestTime(), ride.getRequestTime())
        && Objects.equals(getDepartureTime(), ride.getDepartureTime())
        && Objects.equals(getArrivalTime(), ride.getArrivalTime())
        && Objects.equals(getRideDistance(), ride.getRideDistance())
        && Objects.equals(getPriority(), ride.getPriority())
        && Objects.equals(getRideLength(), ride.getRideLength());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCustomer(), getDriver(), getRequestTime(), getDepartureTime(),
        getArrivalTime(), getRideDistance(), getPriority(), getRideLength());
  }

  @Override
  public String toString() {
    return "Ride{" +
        "customer=" + customer +
        ", driver=" + driver +
        ", requestTime=" + requestTime +
        ", departureTime=" + departureTime +
        ", arrivalTime=" + arrivalTime +
        ", rideDistance=" + rideDistance +
        ", priority=" + priority +
        ", rideLength=" + rideLength +
        '}';
  }
}
