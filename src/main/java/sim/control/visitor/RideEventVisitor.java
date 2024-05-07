package sim.control.visitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import sim.control.calculator.ArrivalTimeCalculator;
import sim.control.exception.EmptyQueueException;
import sim.control.factory.RideFactory;
import sim.model.driver.BasicDriver;
import sim.model.driver.Driver;
import sim.model.event.Event;
import sim.model.event.RideFinishedEvent;
import sim.model.event.RideRequestEvent;
import sim.model.queue.EventQueue;
import sim.model.ride.Ride;
import sim.model.type.RideType;

/**
 * EventVisitor class, process event dequeued form the event queue.
 */
public final class RideEventVisitor implements EventVisitor {

  private final ArrivalTimeCalculator arrivalTimeCalculator;

  /**
   * Constructor for option1.control.visitor.RideEventVisitor class.
   */
  public RideEventVisitor() {
    this.arrivalTimeCalculator = new ArrivalTimeCalculator();
  }

  /**
   * Creates a Ride object using factory and the information encapsulated in the given event.
   *
   * @param event         the given ride finished event
   * @param rideFactories the ride factories
   * @return a Ride object
   */
  private Ride makeRide(RideFinishedEvent event, HashMap<Integer, RideFactory> rideFactories) {
    return rideFactories
        .get(event.getPriority())
        .generateRide(
            event.getDriverName(),
            event.getNumOfRidesFinished(),
            event.getDriverSpeed(),
            event.getCustomerName(),
            event.getStartingLocation(),
            event.getDesiredLocation(),
            event.getRequestTime(),
            event.getDepartureTime(),
            event.getEventTime(),
            event.getRideDistance(),
            event.getRideLength()
        );
  }

  /**
   * Creates a ride finished event with information encapsulated in the ride request event and a
   * given Driver object.
   *
   * @param rideRequestEvent the given ride requested event
   * @param driver           the given driver object
   * @return a ride finished event
   */
  private RideFinishedEvent createRideFinishedEvent(RideRequestEvent rideRequestEvent,
      Driver driver) {
    LocalDateTime arrivalTime = this.arrivalTimeCalculator.calculate(
        rideRequestEvent.getRideDistance(),
        driver.getDriverSpeed(),
        rideRequestEvent.getEventTime());
    return new RideFinishedEvent(
        arrivalTime,
        rideRequestEvent.getCustomerName(),
        rideRequestEvent.getStartingLocation(),
        rideRequestEvent.getDesiredLocation(),
        rideRequestEvent.getRideDistance(),
        rideRequestEvent.getPriority(),
        rideRequestEvent.getEventTime(),
        rideRequestEvent.getEventTime(),
        Duration.between(rideRequestEvent.getEventTime(), arrivalTime).toSeconds(),
        driver.getDriverName(),
        driver.getNumOfRidesFinished(),
        driver.getDriverSpeed());
  }

  /**
   * Creates a ride finished event with information encapsulated in the ride finished event, the
   * ride requested event and a given Driver object.
   *
   * @param rideFinishedEvent the given ride finished event
   * @param rideRequestEvent  the given ride requested event
   * @param driver            the given driver object
   * @return a ride finished event
   */
  private RideFinishedEvent createRideFinishedEvent(RideFinishedEvent rideFinishedEvent,
      RideRequestEvent rideRequestEvent, Driver driver) {
    LocalDateTime arrivalTime = this.arrivalTimeCalculator.calculate(
        rideRequestEvent.getRideDistance(),
        driver.getDriverSpeed(),
        rideFinishedEvent.getEventTime());
    return new RideFinishedEvent(
        arrivalTime,
        rideRequestEvent.getCustomerName(),
        rideRequestEvent.getStartingLocation(),
        rideRequestEvent.getDesiredLocation(),
        rideRequestEvent.getRideDistance(),
        rideRequestEvent.getPriority(),
        rideRequestEvent.getEventTime(),
        rideFinishedEvent.getEventTime(),
        Duration.between(rideFinishedEvent.getEventTime(), arrivalTime).toSeconds(),
        driver.getDriverName(),
        driver.getNumOfRidesFinished(),
        driver.getDriverSpeed());
  }

  /**
   * Visit and process an RideRequestEvent. When a ride request event is dequeued from the event
   * queue, the request event is added to the request queue. If there are available drivers in the
   * driver queue, a ride finished event is created using a request is dequeued from the request
   * queue and a driver is dequeued from a driver queue. The ride finished event is then added back
   * to the event queue.
   *
   * @param event         the Event that needs to be processed
   * @param rideFactories the ride factories used to process the Event
   * @param eventQueue    the event queue used to process the Event
   * @param requestQueue  the request queue used to process the Event
   * @param driverQueue   the driver queue used to process the Event
   * @param rideList      the list of finished rides used to process the Event
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  @Override
  public void visit(
      RideRequestEvent event,
      HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue,
      EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue,
      ArrayList<Ride> rideList) throws EmptyQueueException {
    // Prints ride requested message
    printRideRequestedMessage(event);
    // Adds requestEvent to the request queue
    requestQueue.enqueue(event);
    // If there are available drivers and the request queue is not empty, process request
    if (!requestQueue.isQueueEmpty() && !driverQueue.isEmpty()) {
      // Gets next driver
      Driver driver = driverQueue.poll();
      // Gets next request
      RideRequestEvent rideRequestEvent = requestQueue.dequeue();
      // Creates rideFinishedEvent
      RideFinishedEvent rideFinishedEvent = createRideFinishedEvent(rideRequestEvent, driver);
      // Adds rideFinishedEvent back to the event queue
      eventQueue.enqueue(rideFinishedEvent);
      // Prints ride started message
      printRideStartedMessage(rideFinishedEvent);
    }
  }

  /**
   * Visit and process an RideFinishedEvent. When a ride finished event is dequeued from the event
   * queue, a Ride object is created based on information encapsulated in the finished event and
   * stored into the list of finished rides. The driver assigned to the finished event is then added
   * back to the driver queue, with the numberOfRidesFinished attribute incremented by one. Then, if
   * there are request in the request queue and  available drivers in the driver queue, a new ride
   * finished event is created using a request is dequeued from the request queue and a driver is
   * dequeued from a driver queue. The ride finished event is then added back to the event queue.
   *
   * @param event         the Event that needs to be processed
   * @param rideFactories the ride factories used to process the Event
   * @param eventQueue    the event queue used to process the Event
   * @param requestQueue  the request queue used to process the Event
   * @param driverQueue   the driver queue used to process the Event
   * @param rideList      the list of finished rides used to process the Event
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  @Override
  public void visit(
      RideFinishedEvent event,
      HashMap<Integer, RideFactory> rideFactories,
      EventQueue<Event> eventQueue,
      EventQueue<RideRequestEvent> requestQueue,
      Queue<Driver> driverQueue,
      ArrayList<Ride> rideList) throws EmptyQueueException {
    // Creates completed Ride
    Ride completedRide = makeRide(event, rideFactories);
    // Store completed Ride
    rideList.add(completedRide);
    // Prints ride ended message
    printRideEndedMessage(event);
    // Adds the current driver back to the driver queue
    driverQueue.add(new BasicDriver(event.getDriverName(), event.getNumOfRidesFinished() + 1,
        event.getDriverSpeed()));
    if (!requestQueue.isQueueEmpty() && !driverQueue.isEmpty()) {
      // Gets next driver
      Driver driver = driverQueue.poll();
      // Gets next request
      RideRequestEvent rideRequestEvent = requestQueue.dequeue();
      // Creates rideFinishedEvent
      RideFinishedEvent rideFinishedEvent = createRideFinishedEvent(event, rideRequestEvent,
          driver);
      // Adds rideFinishedEvent back to the event queue
      eventQueue.enqueue(rideFinishedEvent);
      // Prints ride started message
      printRideStartedMessage(rideFinishedEvent);
    }
  }

  /**
   * Prints ride requested message using information encapsulated in the given ride request event.
   *
   * @param rideRequestEvent the given ride request event
   */
  private void printRideRequestedMessage(RideRequestEvent rideRequestEvent) {
    System.out.println(
        "[RIDE REQUESTED]" + " [Request Time:   " + rideRequestEvent.getEventTime() + "]"
            + " [Customer: " + rideRequestEvent.getCustomerName() + "]"
            + " [Ride Type: " + RideType.getRideType(rideRequestEvent.getPriority()) + "]"
            + " [From: " + rideRequestEvent.getStartingLocation() + "]"
            + " [To: " + rideRequestEvent.getDesiredLocation() + "]");
  }

  /**
   * Prints ride started message using information encapsulated in the given ride finished event.
   *
   * @param rideFinishedEvent the given ride finished event
   */
  private void printRideStartedMessage(RideFinishedEvent rideFinishedEvent) {
    System.out.println(
        "[RIDE STARTED]  " + " [Departure Time: " + rideFinishedEvent.getDepartureTime() + "]"
            + " [Customer: " + rideFinishedEvent.getCustomerName() + "]"
            + " [Ride Type: " + RideType.getRideType(rideFinishedEvent.getPriority()) + "]"
            + " [From: " + rideFinishedEvent.getStartingLocation() + "]"
            + " [To: " + rideFinishedEvent.getDesiredLocation() + "]");
  }

  /**
   * Prints ride ended message using information encapsulated in the given ride finished event.
   *
   * @param rideFinishedEvent the given ride finished event
   */
  private void printRideEndedMessage(RideFinishedEvent rideFinishedEvent) {
    System.out.println(
        "[RIDE ENDED]    " + " [Arrival Time:   " + rideFinishedEvent.getEventTime() + "]"
            + " [Customer: " + rideFinishedEvent.getCustomerName() + "]"
            + " [Ride Type: " + RideType.getRideType(rideFinishedEvent.getPriority()) + "]"
            + " [From: " + rideFinishedEvent.getStartingLocation() + "]"
            + " [To: " + rideFinishedEvent.getDesiredLocation() + "]");
  }
}
