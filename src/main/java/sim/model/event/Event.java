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
 * Abstract Event class, representing an event in the simulation.
 */
public abstract class Event {

  protected final LocalDateTime eventTime;
  protected final String customerName;
  protected final String startingLocation;
  protected final String desiredLocation;
  protected final Double rideDistance;
  protected final Integer priority;

  /**
   * Constructor for option1.model.event.Event class.
   *
   * @param eventTime        time used to sort the event
   * @param customerName     the name of the customer encapsulated in this event
   * @param startingLocation the starting location of a ride encapsulated in this event
   * @param desiredLocation  the end location of a ride encapsulated in this event
   * @param rideDistance     the distance covered by the ride in miles encapsulated in this event
   * @param priority         the priority (type) of a ride encapsulated in this event
   */
  public Event(LocalDateTime eventTime, String customerName, String startingLocation,
      String desiredLocation, Double rideDistance, Integer priority) {
    this.eventTime = eventTime;
    this.customerName = customerName;
    this.startingLocation = startingLocation;
    this.desiredLocation = desiredLocation;
    this.rideDistance = rideDistance;
    this.priority = priority;
  }

  /**
   * Gets the time used to sort the event.
   *
   * @return the time used to sort the event
   */
  public LocalDateTime getEventTime() {
    return eventTime;
  }

  /**
   * Gets the name of the customer.
   *
   * @return the name of the customer
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * Gets the starting location.
   *
   * @return the starting location
   */
  public String getStartingLocation() {
    return startingLocation;
  }

  /**
   * Gets the end location.
   *
   * @return the end location
   */
  public String getDesiredLocation() {
    return desiredLocation;
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
   * Gets the priority of the ride.
   *
   * @return the priority of the ride
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Abstract method. Accepts a visitor class. The visitor class process event based on its runtime
   * type.
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
  public abstract void accept(
      EventVisitor visitor,
      HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue,
      EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue,
      ArrayList<Ride> rideList) throws EmptyQueueException;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(getEventTime(), event.getEventTime())
        && Objects.equals(getCustomerName(), event.getCustomerName())
        && Objects.equals(getStartingLocation(), event.getStartingLocation())
        && Objects.equals(getDesiredLocation(), event.getDesiredLocation())
        && Objects.equals(getRideDistance(), event.getRideDistance())
        && Objects.equals(getPriority(), event.getPriority());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEventTime(), getCustomerName(), getStartingLocation(),
        getDesiredLocation(), getRideDistance(), getPriority());
  }

  @Override
  public String toString() {
    return "Event{" +
        "eventTime=" + eventTime +
        ", customerName='" + customerName + '\'' +
        ", startingLocation='" + startingLocation + '\'' +
        ", desiredLocation='" + desiredLocation + '\'' +
        ", rideDistance=" + rideDistance +
        ", priority=" + priority +
        '}';
  }
}
