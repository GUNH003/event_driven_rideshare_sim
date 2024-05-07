package sim.model.queue;

import sim.control.exception.EmptyQueueException;
import sim.model.event.Event;

/**
 * Interface for event priority queues.
 *
 * @param <T> type of element store in the priority queue, any subtype of Event is allowed
 */
public interface EventQueue<T extends Event> {

  /**
   * Enqueues the given event.
   *
   * @param event the given event.
   */
  void enqueue(T event);

  /**
   * Dequeues the event with the highest priority.
   *
   * @return the event with the highest priority.
   * @throws EmptyQueueException throws EmptyQueueException when the priority queue is empty
   */
  T dequeue() throws EmptyQueueException;

  /**
   * Checks if the priority queue is empty.
   *
   * @return true if the priority queue is empty, false otherwise
   */
  Boolean isQueueEmpty();
}
