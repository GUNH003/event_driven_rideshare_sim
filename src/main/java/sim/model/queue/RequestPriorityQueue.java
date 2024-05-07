package sim.model.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import sim.control.comparator.RequestComparator;
import sim.control.exception.EmptyQueueException;
import sim.model.event.RideRequestEvent;
import sim.model.type.RideType;

/**
 * RequestPriorityQueue class, representing a priority queue that stores customers' request event
 * for simulation. This is an abstract ADT that implements customized scheduling algorithm using
 * comparator object.
 *
 * @param <T> type of element store in the priority queue, any subtype of RideRequestEvent is
 *            allowed
 */
public class RequestPriorityQueue<T extends RideRequestEvent> extends
    ArrayList<PriorityQueue<T>> implements EventQueue<T> {

  private static final Integer NUM_PRIORITY_QUEUES = 4;
  private static final Integer EXPRESS_QUANTUM = 10;
  private static final Integer STANDARD_QUANTUM = 7;
  private static final Integer WAIT_AND_SAVE_QUANTUM = 5;
  private static final Integer ENVIRONMENTALLY_CONSCIOUS_QUANTUM = 3;

  private Queue<Integer> indices;
  private HashMap<Integer, Integer> countMap;
  private HashMap<Integer, Integer> quantumMap;

  /**
   * Constructor for option1.model.queue.RequestPriorityQueue class. This class contains four
   * priority queues, corresponding to four different ride types. Priority queue at index 0 contains
   * request for express rides. Priority queue at index 1 contains requests for standard rides.
   * Priority queue at index 2 contains requests for wait and save rides. Priority queue at index 3
   * contains requests for environmentally conscious rides. Within each priority queue, a
   * combination of Shortest Job First (SJF) and First Come First Served (FCFS) scheduling
   * method is used. The ride request with a shorter distance has a higher priority. For rides
   * request with the same distance, earlier request time yields higher priority. In addition, a
   * weighted Round Robin (RR) method is used to prevent lower priority queue from starving by
   * rotating the queues. Each priority queue is assigned with a specific service quantum. In order
   * to preserve the rule that higher priority queue should get more resources, the queue with
   * higher priority is assigned with a higher quantum and vice-versa. This makes sure that the
   * higher priority queue has a higher probability of being served.
   */
  public RequestPriorityQueue() {
    initializePriorityQueues();
    initializeIndices();
    initializeCountMap();
    initializeQuantumMap();
  }

  /**
   * Initializes each priority queue.
   */
  private void initializePriorityQueues() {
    RequestComparator<T> requestComparator = new RequestComparator<>();
    for (int i = 0; i < NUM_PRIORITY_QUEUES; i++) {
      this.add(new PriorityQueue<>(requestComparator));
    }
  }

  /**
   * Initializes the index queue used for priority queue rotation.
   */
  private void initializeIndices() {
    this.indices = new LinkedList<>();
    for (int i = 0; i < NUM_PRIORITY_QUEUES; i++) {
      this.indices.add(i);
    }
  }

  /**
   * Initialize the count map to record the service quantum for each queue. The quantum for each
   * priority queue is initialized to zero at the beginning.
   */
  private void initializeCountMap() {
    this.countMap = new HashMap<>();
    for (int i = 0; i < NUM_PRIORITY_QUEUES; i++) {
      this.countMap.put(i, 0);
    }
  }

  /**
   * Initializes the quantum map that record the maximum service quantum that each queue can reach
   * before providing service to the next queue in each iteration.
   */
  private void initializeQuantumMap() {
    this.quantumMap = new HashMap<>();
    for (int i = 0; i < NUM_PRIORITY_QUEUES; i++) {
      if (i == RideType.EXPRESS_PICK_UP.getPriority()) {
        this.quantumMap.put(i, EXPRESS_QUANTUM);
      }
      if (i == RideType.STANDARD_PICK_UP.getPriority()) {
        this.quantumMap.put(i, STANDARD_QUANTUM);
      }
      if (i == RideType.WAIT_AND_SAVE_PICK_UP.getPriority()) {
        this.quantumMap.put(i, WAIT_AND_SAVE_QUANTUM);
      }
      if (i == RideType.ENVIRONMENTALLY_CONSCIOUS_PICK_UP.getPriority()) {
        this.quantumMap.put(i, ENVIRONMENTALLY_CONSCIOUS_QUANTUM);
      }
    }
  }

  /**
   * Enqueues a ride request event. It uses generics to ensure compile time type safety.
   *
   * @param request the ride request.
   */
  @Override
  public void enqueue(T request) {
    if (request.getPriority().equals(RideType.EXPRESS_PICK_UP.getPriority())) {
      this.get(RideType.EXPRESS_PICK_UP.getPriority()).add(request);
    }
    if (request.getPriority().equals(RideType.STANDARD_PICK_UP.getPriority())) {
      this.get(RideType.STANDARD_PICK_UP.getPriority()).add(request);
    }
    if (request.getPriority().equals(RideType.WAIT_AND_SAVE_PICK_UP.getPriority())) {
      this.get(RideType.WAIT_AND_SAVE_PICK_UP.getPriority()).add(request);
    }
    if (request.getPriority().equals(RideType.ENVIRONMENTALLY_CONSCIOUS_PICK_UP.getPriority())) {
      this.get(RideType.ENVIRONMENTALLY_CONSCIOUS_PICK_UP.getPriority()).add(request);
    }
  }

  /**
   * Checks if the priority queue is empty.
   *
   * @return true if the priority queue is empty, false otherwise
   */
  @Override
  public Boolean isQueueEmpty() {
    for (PriorityQueue<T> pq : this) {
      if (!pq.isEmpty()) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  /**
   * Dequeues the request event with the highest priority.
   *
   * @return the request with the highest priority
   * @throws EmptyQueueException throws EmptyQueueException when the priority queue is empty
   */
  @Override
  public T dequeue() throws EmptyQueueException {
    if (this.isQueueEmpty()) {
      throw new EmptyQueueException("Cannot dequeue from empty queue.");
    } else {
      return getNextRequest();
    }
  }

  /**
   * Helper method. Gets the index of the next queue that is going to be served.
   *
   * @return the index of the next queue that is going to be served
   */
  private Integer getNextIndex() {
    Integer runningIndex = this.indices.peek();
    while (this.countMap.get(runningIndex) >= this.quantumMap.get(runningIndex)
        || this.get(runningIndex).isEmpty()) {
      this.countMap.put(this.indices.peek(), 0);
      this.indices.add(this.indices.poll());
      runningIndex = this.indices.peek();
    }
    return runningIndex;
  }

  /**
   * Helper method. Gets the next rideRequest from the currently served queue.
   *
   * @return the next rideRequest from the currently served queue
   */
  private T getNextRequest() {
    Integer index = this.getNextIndex();
    T rideRequest = this.get(index).poll();
    this.countMap.put(index, countMap.get(index) + 1); // updates service quantum
    return rideRequest;
  }
}
