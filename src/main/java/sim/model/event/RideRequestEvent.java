package sim.model.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import sim.control.exception.EmptyQueueException;
import sim.control.factory.RideFactory;
import sim.control.visitor.EventVisitor;
import sim.model.driver.Driver;
import sim.model.queue.EventQueue;
import sim.model.ride.Ride;

/**
 * RideRequestEvent class, representing a ride request event in the simulation.
 */
public final class RideRequestEvent extends Event {

  /**
   * Constructor for option1.model.event.RideRequestEvent class.
   *
   * @param eventTime        time used to sort the event
   * @param customerName     the name of the customer encapsulated in this event
   * @param startingLocation the starting location of a ride encapsulated in this event
   * @param desiredLocation  the end location of a ride encapsulated in this event
   * @param rideDistance     the distance covered by the ride in miles encapsulated in this event
   * @param priority         the priority (type) of a ride encapsulated in this event
   */
  public RideRequestEvent(LocalDateTime eventTime, String customerName, String startingLocation,
      String desiredLocation, Double rideDistance, Integer priority) {
    super(eventTime, customerName, startingLocation, desiredLocation, rideDistance, priority);
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
  public String toString() {
    return "RideRequestEvent{} " + super.toString();
  }
}
