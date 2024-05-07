package sim.model.data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * MockDataGenerator class, generates mock data based on the raw data.
 */
public final class MockDataGenerator {

  private final ArrayList<String> names;
  private final ArrayList<String> addresses;

  /**
   * Constructor for option1.model.data.MockDataGenerator. It generates mock data used in the
   * simulation. The mock data generated are names and addresses.
   */
  public MockDataGenerator() {
    this.names = new ArrayList<>();
    this.addresses = new ArrayList<>();
  }

  /**
   * Gets the name data.
   *
   * @return the name data.
   */
  public ArrayList<String> getNames() {
    return names;
  }

  /**
   * Gets the address data.
   *
   * @return the address data
   */
  public ArrayList<String> getAddresses() {
    return addresses;
  }

  /**
   * Generates name and address data. The names are generated with different permutations of first
   * name and last name.
   */
  public void generate() {
    MockDataReader reader = new MockDataReader();
    reader.readMockData(0, 1, 3);
    for (String firstName : reader.getFirstNames()) {
      for (String lastName : reader.getLastNames()) {
        String stringBuilder = firstName + " " + lastName;
        this.names.add(stringBuilder);
      }
    }
    this.addresses.addAll(reader.getAddresses());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MockDataGenerator that = (MockDataGenerator) o;
    return Objects.equals(getNames(), that.getNames())
        && Objects.equals(getAddresses(), that.getAddresses());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNames(), getAddresses());
  }

  @Override
  public String toString() {
    return "MockDataGenerator{" +
        "names=" + names +
        ", addresses=" + addresses +
        '}';
  }
}
