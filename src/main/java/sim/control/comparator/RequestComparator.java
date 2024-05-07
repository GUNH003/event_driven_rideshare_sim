package sim.control.comparator;

import java.util.Comparator;
import sim.model.event.RideRequestEvent;

/**
 * RequestComparator class, compares two RideRequestEvent objects.
 *
 * @param <T> type of object allowed for comparison, any subtype of RideRequestEvent is allowed
 */
public final class RequestComparator<T extends RideRequestEvent> implements Comparator<T> {

  public RequestComparator() {
  }

  /**
   * Compares two RideRequestEvent objects. It first compares the distance encapsulated in the two
   * objects. If the distances are equal, it compares the eventTime attribute.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return negative if o1 is less than o2, zero if o1 is equal to o2, positive if o1 is greater
   * than o2
   */
  @Override
  public int compare(T o1, T o2) {
    int res = Double.compare(o1.getRideDistance(), o2.getRideDistance());
    if (res == 0) {
      return o1.getEventTime().compareTo(o2.getEventTime());
    } else {
      return res;
    }
  }
}
