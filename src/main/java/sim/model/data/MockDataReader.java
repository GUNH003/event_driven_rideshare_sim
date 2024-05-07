package sim.model.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MockDataReader class, reads the csv that contains raw data.
 */
public final class MockDataReader {

  private static final String CSV_FILE_REGEX = "\"([^\"]*)\"";
  private static final String MOCK_DATA_FILE_PATH =
      "./src/main/resources/mock/mock_data.csv";

  private final ArrayList<String> firstNames;
  private final ArrayList<String> lastNames;
  private final ArrayList<String> addresses;

  /**
   * Constructor for option1.model.data.MockDataReader class. It uses buffer reader to read the csv
   * file and extract data used in the simulation.
   */
  public MockDataReader() {
    this.firstNames = new ArrayList<>();
    this.lastNames = new ArrayList<>();
    this.addresses = new ArrayList<>();
  }

  /**
   * Reads the raw data file. It calls the csvReader to read the csv file.
   *
   * @param firstNameColumn the number of the column that contains first name
   * @param lastNameColumn  the number of the column that contains last name
   * @param addressColumn   the number of the column that contains address information
   */
  public void readMockData(Integer firstNameColumn, Integer lastNameColumn, Integer addressColumn) {
    csvReader(firstNameColumn, lastNameColumn, addressColumn);
  }

  /**
   * Reads the csv file then extracts first name, last name and address.
   *
   * @param firstNameColumn the number of the column that contains first name
   * @param lastNameColumn  the number of the column that contains last name
   * @param addressColumn   the number of the column that contains address information
   */
  private void csvReader(Integer firstNameColumn, Integer lastNameColumn, Integer addressColumn) {
    try (BufferedReader br = new BufferedReader(new FileReader(MOCK_DATA_FILE_PATH))) {
      br.readLine();
      Pattern pattern = Pattern.compile(CSV_FILE_REGEX);
      String line;
      while ((line = br.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        int i = 0;
        while (matcher.find()) {
          if (i == firstNameColumn) {
            this.firstNames.add(matcher.group(1));
          } else if (i == lastNameColumn) {
            this.lastNames.add(matcher.group(1));
          } else if (i == addressColumn) {
            this.addresses.add(matcher.group(1));
          } else if (i > addressColumn) {
            break;
          }
          i++;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the first names.
   *
   * @return the first names
   */
  public ArrayList<String> getFirstNames() {
    return firstNames;
  }

  /**
   * Gets the last names.
   *
   * @return the last names
   */
  public ArrayList<String> getLastNames() {
    return lastNames;
  }

  /**
   * Gets the addresses.
   *
   * @return the addresses
   */
  public ArrayList<String> getAddresses() {
    return addresses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MockDataReader that = (MockDataReader) o;
    return Objects.equals(getFirstNames(), that.getFirstNames())
        && Objects.equals(getLastNames(), that.getLastNames())
        && Objects.equals(getAddresses(), that.getAddresses());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstNames(), getLastNames(), getAddresses());
  }

  @Override
  public String toString() {
    return "MockDataReader{" +
        "firstNames=" + firstNames +
        ", lastNames=" + lastNames +
        ", addresses=" + addresses +
        '}';
  }
}
