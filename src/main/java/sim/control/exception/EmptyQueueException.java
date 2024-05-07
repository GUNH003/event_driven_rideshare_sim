package sim.control.exception;

/**
 * EmptyQueueException class, thrown when dequeue from an empty queue.
 */
public class EmptyQueueException extends Exception {

  /**
   * Constructor for option1.control.exception.EmptyQueueException class.
   *
   * @param message the error message
   */
  public EmptyQueueException(String message) {
    super(message);
  }
}
