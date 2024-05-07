package sim.control.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import sim.control.exception.EmptyQueueException;
import sim.control.factory.RideFactory;
import sim.model.driver.Driver;
import sim.model.event.Event;
import sim.model.event.RideFinishedEvent;
import sim.model.event.RideRequestEvent;
import sim.model.queue.EventQueue;
import sim.model.ride.Ride;

/**
 * Interface for Visitor. Because the event queue contains objects of subtypes of Event,
 * EventVisitor uses overloaded method to process different types of Event object in runtime. This
 * promotes loose coupling and avoids unsafe down casting.
 */
public interface EventVisitor {

  /**
   * Visit and process an RideRequestEvent.
   *
   * @param event         the Event that needs to be processed
   * @param rideFactories the ride factories used to process the Event
   * @param eventQueue    the event queue used to process the Event
   * @param requestQueue  the request queue used to process the Event
   * @param driverQueue   the driver queue used to process the Event
   * @param rideList      the list of finished rides used to process the Event
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  void visit(RideRequestEvent event, HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue, EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue, ArrayList<Ride> rideList) throws EmptyQueueException;

  /**
   * Visit and process an RideFinishedEvent.
   *
   * @param event         the Event that needs to be processed
   * @param rideFactories the ride factories used to process the Event
   * @param eventQueue    the event queue used to process the Event
   * @param requestQueue  the request queue used to process the Event
   * @param driverQueue   the driver queue used to process the Event
   * @param rideList      the list of finished rides used to process the Event
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  void visit(RideFinishedEvent event, HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue, EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue, ArrayList<Ride> rideList) throws EmptyQueueException;
}
