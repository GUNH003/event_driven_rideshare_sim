package sim.model.queue;

import java.util.PriorityQueue;
import sim.control.comparator.EventComparator;
import sim.control.exception.EmptyQueueException;
import sim.model.event.Event;

/**
 * EventPriorityQueue class, representing a priority queue that stores event for simulation. The
 * events are compared based on the eventTime.
 *
 * @param <T> type of element store in the priority queue, any subtype of Event is allowed
 */
public class EventPriorityQueue<T extends Event> extends PriorityQueue<T> implements EventQueue<T> {

  /**
   * Constructor for option1.model.queue.EventPriorityQueue class.
   */
  public EventPriorityQueue() {
    super(new EventComparator());
  }

  /**
   * Checks if the priority queue is empty.
   *
   * @return true if the priority queue is empty, false otherwise
   */
  @Override
  public Boolean isQueueEmpty() {
    return this.isEmpty();
  }

  /**
   * Enqueues the given event.
   *
   * @param event the given event.
   */
  @Override
  public void enqueue(T event) {
    this.add(event);
  }

  /**
   * Dequeues the event with the highest priority.
   *
   * @return the event with the highest priority.
   * @throws EmptyQueueException throws EmptyQueueException when the priority queue is empty
   */
  @Override
  public T dequeue() throws EmptyQueueException {
    if (this.isEmpty()) {
      throw new EmptyQueueException("Dequeue from empty queue.");
    }
    return this.poll();
  }
}
