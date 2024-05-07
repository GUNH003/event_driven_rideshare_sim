package sim.control.mediator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import sim.control.calculator.DistanceCalculator;
import sim.control.exception.EmptyQueueException;
import sim.control.factory.EnvironmentallyConsciousRideFactory;
import sim.control.factory.ExpressRideFactory;
import sim.control.factory.RideFactory;
import sim.control.factory.StandardRideFactory;
import sim.control.factory.WaitAndSaveRideFactory;
import sim.control.visitor.EventVisitor;
import sim.control.visitor.RideEventVisitor;
import sim.model.data.MockDataGenerator;
import sim.model.driver.BasicDriver;
import sim.model.driver.Driver;
import sim.model.event.Event;
import sim.model.event.RideRequestEvent;
import sim.model.queue.EventPriorityQueue;
import sim.model.queue.EventQueue;
import sim.model.queue.RequestPriorityQueue;
import sim.model.ride.Ride;
import sim.model.type.RideType;

/**
 * SimDirector class, manages interaction and behaviors of multiple stockholders for the
 * simulation.
 */
public class SimDirector {

  private static final Integer MINUTES_IN_HOUR = 60;
  private static final Integer SECONDS_IN_MIN = 60;
  private static final Integer NUM_OF_PRIORITIES = 4;       // number of priorities levels (ride types)
  private static final Integer NUM_OF_FACTORIES = 4;        // number of ride factories
  private static final Integer SIM_TIME_SPAN = 60;          // 120 minutes
  private static final Double DRIVER_SPEED = 60.0;          // 60 mph
  private static final Double DISTANCE_UPPER_BOUND = 120.0; // 25 miles
  private static final Double INVALID_AVERAGE = -1.0;       // invalid average time
  private static final String USER_INPUT_REGEX = "\\d+";    // regEx for input matching

  private final DistanceCalculator distanceCalculator;
  private final MockDataGenerator mockDataGenerator;
  private final HashMap<Integer, RideFactory> rideFactories;
  private final EventQueue<Event> eventQueue;
  private final EventQueue<RideRequestEvent> requestQueue;
  private final Queue<Driver> driverQueue;
  private final ArrayList<Ride> rideList;

  /**
   * Constructor for option1.control.mediator.SimDirector class.
   */
  public SimDirector() {
    this.distanceCalculator = new DistanceCalculator();
    this.mockDataGenerator = new MockDataGenerator();
    this.rideFactories = new HashMap<>();
    this.eventQueue = new EventPriorityQueue<>();
    this.requestQueue = new RequestPriorityQueue<>();
    this.driverQueue = new LinkedList<>();
    this.rideList = new ArrayList<>();
  }

  /**
   * Runs the simulation.
   *
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  public void run() throws EmptyQueueException {
    int numOfDrivers;
    int numOfCustomers;
    // Gets user input for number of drivers
    while (true) {
      System.out.println("Please enter the number of drivers: ");
      String command = scanInput();
      if (!(command.matches(USER_INPUT_REGEX) && !command.equals("0"))) {
        System.out.println("Number of drivers must be positive integers.");
      } else {
        numOfDrivers = Integer.parseInt(command);
        break;
      }
    }
    // Gets user input for number of customers
    while (true) {
      System.out.println("Please enter the number of customers: ");
      String command = scanInput();
      if (!(command.matches(USER_INPUT_REGEX) && !command.equals("0"))) {
        System.out.println("Number of customers must be positive integers.");
      } else {
        numOfCustomers = Integer.parseInt(command);
        break;
      }
    }
    // Runs the simulation
    this.runSimMainLoop(numOfDrivers, numOfCustomers);
    // Prints result
    System.out.println("-----------------------------------------------------------------");
    System.out.println("Simulation completed.");
    System.out.printf(
        "Total number of rides served: %d\n", this.calculateTotalNumberOfRidesServed());
    System.out.printf(
        "Average wait time for a ride: %02dh%02dm%02ds\n",
        convertSecondsToHMS(this.calculateAverageWaitingTime()).get(0),
        convertSecondsToHMS(this.calculateAverageWaitingTime()).get(1),
        convertSecondsToHMS(this.calculateAverageWaitingTime()).get(2));
    System.out.printf(
        "Average number of rides handled per driver: %.2f\n",
        this.calculateAverageNumberOfRidesPerDriver());
  }

  /**
   * Scans the user input. It uses scanner to scan user input.
   *
   * @return the user input
   */
  private String scanInput() {
    Scanner inputScan = new Scanner(System.in);
    return inputScan.nextLine();
  }

  /**
   * Converts a Duration of seconds into a vector of integers that contains hours, minutes and
   * seconds.
   *
   * @param seconds a Duration of seconds
   * @return a vector of integers that contains hours, minutes and seconds
   */
  private ArrayList<Integer> convertSecondsToHMS(Double seconds) {
    ArrayList<Integer> res = new ArrayList<>();
    int s = (int) (seconds % SECONDS_IN_MIN);
    int m = (int) ((seconds / SECONDS_IN_MIN) % MINUTES_IN_HOUR);
    int h = (int) ((seconds / SECONDS_IN_MIN) / MINUTES_IN_HOUR);
    res.add(h);
    res.add(m);
    res.add(s);
    return res;
  }

  /**
   * Initializes simulation with the given number of drivers and the given number of initial ride
   * requested events.
   *
   * @param numOfDrivers         the given number of drivers
   * @param numOfInitialRequests the given number of initial ride requested events
   */
  private void initializeSim(Integer numOfDrivers, Integer numOfInitialRequests) {
    initializeMockDataGenerator();
    populateRideFactories();
    populateEventQueue(numOfInitialRequests);
    populateDriverQueue(numOfDrivers);
  }

  /**
   * Initializes mock data generator.
   */
  private void initializeMockDataGenerator() {
    this.mockDataGenerator.generate();
  }

  /**
   * Creates the ride factories used to create different rides.
   */
  private void populateRideFactories() {
    for (int i = 0; i < NUM_OF_FACTORIES; i++) {
      if (i == RideType.EXPRESS_PICK_UP.getPriority()) {
        this.rideFactories.put(i, new ExpressRideFactory());
      }
      if (i == RideType.STANDARD_PICK_UP.getPriority()) {
        this.rideFactories.put(i, new StandardRideFactory());
      }
      if (i == RideType.WAIT_AND_SAVE_PICK_UP.getPriority()) {
        this.rideFactories.put(i, new WaitAndSaveRideFactory());
      }
      if (i == RideType.ENVIRONMENTALLY_CONSCIOUS_PICK_UP.getPriority()) {
        this.rideFactories.put(i, new EnvironmentallyConsciousRideFactory());
      }
    }
  }

  /**
   * Populates the event queue with the given number of initial ride requested event.
   *
   * @param numOfInitialRequests the given number of initial ride requested event
   */
  private void populateEventQueue(Integer numOfInitialRequests) {
    Random rnd = new Random();
    ArrayList<String> names = this.mockDataGenerator.getNames();
    ArrayList<String> addresses = this.mockDataGenerator.getAddresses();
    LocalDateTime timeZero = LocalDateTime.now();
    for (int i = 0; i < numOfInitialRequests; i++) {
      LocalDateTime eventTime = timeZero.plusMinutes(rnd.nextInt(SIM_TIME_SPAN));
      String name = names.get(rnd.nextInt(names.size()));
      String startingLocation = addresses.get(rnd.nextInt(addresses.size()));
      String desiredLocation = addresses.get(rnd.nextInt(addresses.size()));
      Double distance = this.distanceCalculator.calculateDistance(DISTANCE_UPPER_BOUND);
      Integer priority = rnd.nextInt(NUM_OF_PRIORITIES);
      Event rideRequestEvent = new RideRequestEvent(
          eventTime,
          name,
          startingLocation,
          desiredLocation,
          distance,
          priority);
      this.eventQueue.enqueue(rideRequestEvent);
    }
  }

  /**
   * Populates the number of drivers with the given number of drivers.
   *
   * @param numOfDrivers the given number of drivers
   */
  private void populateDriverQueue(Integer numOfDrivers) {
    Random rnd = new Random();
    ArrayList<String> names = this.mockDataGenerator.getNames();
    for (int i = 0; i < numOfDrivers; i++) {
      String name = names.get(rnd.nextInt(names.size()));
      this.driverQueue.add(new BasicDriver(name, DRIVER_SPEED));
    }
  }

  /**
   * Runs the main loop of simulation with the given number of drivers and the given number of
   * initial ride requested event.
   *
   * @param numOfDrivers         the given number of drivers
   * @param numOfInitialRequests the given number of initial ride requested event
   * @throws EmptyQueueException throw EmptyQueueException when dequeue from an empty queue
   */
  private void runSimMainLoop(Integer numOfDrivers, Integer numOfInitialRequests)
      throws EmptyQueueException {
    // Initializes simulation
    initializeSim(numOfDrivers, numOfInitialRequests);
    // Creates visitor for simulation
    EventVisitor eventVisitor = new RideEventVisitor();
    // Begin sim main loop
    while (!this.eventQueue.isQueueEmpty() || !this.requestQueue.isQueueEmpty()) {
      // Gets next event
      Event nextEvent = this.eventQueue.dequeue();
      // Visitor handles the event based on event type
      nextEvent.accept(
          eventVisitor,
          this.rideFactories,
          this.eventQueue,
          this.requestQueue,
          this.driverQueue,
          this.rideList);
    }
  }

  /**
   * Calculates the average waiting time in seconds for the customers.
   *
   * @return the average waiting time
   */
  private Double calculateAverageWaitingTime() {
    return this.rideList.stream()
        .mapToDouble(r -> Duration.between(r.getRequestTime(), r.getDepartureTime()).toSeconds())
        .average()
        .orElse(INVALID_AVERAGE);
  }

  /**
   * Calculates the average number of rides handled per driver.
   *
   * @return the average number of rides handled per driver
   */
  private Double calculateAverageNumberOfRidesPerDriver() {
    int size = this.driverQueue.size();
    int runningSum = 0;
    while (!this.driverQueue.isEmpty()) {
      runningSum += driverQueue.poll().getNumOfRidesFinished();
    }
    return (double) runningSum / size;
  }

  /**
   * Calculates the total number of rides served in the simulation.
   *
   * @return the total number of rides served
   */
  private Integer calculateTotalNumberOfRidesServed() {
    return this.rideList.size();
  }
}
