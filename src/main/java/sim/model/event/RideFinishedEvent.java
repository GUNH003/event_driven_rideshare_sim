package sim.model.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;
import sim.control.exception.EmptyQueueException;
import sim.control.factory.RideFactory;
import sim.control.visitor.EventVisitor;
import sim.model.driver.Driver;
import sim.model.queue.EventQueue;
import sim.model.ride.Ride;

/**
 * RideFinishedEvent class, representing a finished ride event in the simulation.
 */
public final class RideFinishedEvent extends Event {

  private final LocalDateTime requestTime;
  private final LocalDateTime departureTime;
  private final Long rideLength;
  private final String driverName;
  private final Integer numOfRidesFinished;
  private final Double driverSpeed;

  /**
   * Constructor for option1.model.event.RideFinishedEvent class.
   *
   * @param eventTime          time used to sort the event
   * @param customerName       the name of the customer encapsulated in this event
   * @param startingLocation   the starting location of a ride encapsulated in this event
   * @param desiredLocation    the end location of a ride encapsulated in this event
   * @param rideDistance       the distance covered by the ride in miles encapsulated in this event
   * @param priority           the priority (type) of a ride encapsulated in this event
   * @param requestTime        the time of the ride request was created
   * @param departureTime      the time of departure
   * @param rideLength         the duration of the ride
   * @param driverName         the name of the driver assigned to the ride
   * @param numOfRidesFinished the number of rides finished by the driver
   * @param driverSpeed        the driving speed of the driver
   */
  public RideFinishedEvent(LocalDateTime eventTime, String customerName, String startingLocation,
      String desiredLocation, Double rideDistance, Integer priority, LocalDateTime requestTime,
      LocalDateTime departureTime, Long rideLength, String driverName, Integer numOfRidesFinished,
      Double driverSpeed) {
    super(eventTime, customerName, startingLocation, desiredLocation, rideDistance, priority);
    this.requestTime = requestTime;
    this.departureTime = departureTime;
    this.rideLength = rideLength;
    this.driverName = driverName;
    this.numOfRidesFinished = numOfRidesFinished;
    this.driverSpeed = driverSpeed;
  }

  /**
   * Gets the time of the ride request was created.
   *
   * @return the time of the ride request was created
   */
  public LocalDateTime getRequestTime() {
    return requestTime;
  }

  /**
   * Gets the time of departure.
   *
   * @return the time of departure
   */
  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Gets the duration of the ride.
   *
   * @return the duration of the ride
   */
  public Long getRideLength() {
    return rideLength;
  }

  /**
   * Gets the name of the driver assigned to the ride.
   *
   * @return the name of the driver assigned to the ride
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

  /**
   * Accepts a visitor class. The visitor class process event based on its runtime type.
   *
   * @param visitor       the visitor
   * @param rideFactories the map containing ride factories
   * @param eventQueue    the event priority queue
   * @param requestQueue  the request priority queue
   * @param driverQueue   the driver queue
   * @param rideList      the list including all finished rides
   * @throws EmptyQueueException throws EmptyQueueException when event queue or request queue is
   *                             empty
   */
  @Override
  public void accept(
      EventVisitor visitor,
      HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue,
      EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue,
      ArrayList<Ride> rideList) throws EmptyQueueException {
    visitor.visit(
        this,
        rideFactories,
        eventQueue,
        requestQueue,
        driverQueue,
        rideList);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    RideFinishedEvent that = (RideFinishedEvent) o;
    return Objects.equals(getRequestTime(), that.getRequestTime())
        && Objects.equals(getDepartureTime(), that.getDepartureTime())
        && Objects.equals(getRideLength(), that.getRideLength())
        && Objects.equals(getDriverName(), that.getDriverName())
        && Objects.equals(getNumOfRidesFinished(), that.getNumOfRidesFinished())
        && Objects.equals(getDriverSpeed(), that.getDriverSpeed());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getRequestTime(), getDepartureTime(), getRideLength(),
        getDriverName(), getNumOfRidesFinished(), getDriverSpeed());
  }

  @Override
  public String toString() {
    return "RideFinishedEvent{" +
        "requestTime=" + requestTime +
        ", departureTime=" + departureTime +
        ", rideLength=" + rideLength +
        ", driverName='" + driverName + '\'' +
        ", numOfRidesFinished=" + numOfRidesFinished +
        ", driverSpeed=" + driverSpeed +
        "} " + super.toString();
  }
}
