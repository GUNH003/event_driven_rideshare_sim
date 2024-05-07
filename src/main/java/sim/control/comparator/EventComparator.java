package sim.control.comparator;

import java.util.Comparator;
import sim.model.event.Event;

/**
 * EventComparator class, compares two Event objects.
 */
public final class EventComparator implements Comparator<Event> {

  /**
   * Constructor for option1.control.comparator.EventComparator class.
   */
  public EventComparator() {
  }

  /**
   * Compares the two Event objects based on the eventTime attribute.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return negative if o1 is less than o2, zero if o1 is equal to o2, positive if o1 is greater
   * than o2
   */
  @Override
  public int compare(Event o1, Event o2) {
    return o1.getEventTime().compareTo(o2.getEventTime());
  }
}
