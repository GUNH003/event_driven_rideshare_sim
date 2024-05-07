package sim.model.customer;

import java.util.Objects;

/**
 * Abstract Customer class, representing a customer.
 */
public abstract class Customer {

  protected final String customerName;
  protected final String startingLocation;
  protected final String desiredLocation;

  /**
   * Constructor for option1.model.customer.Customer class.
   *
   * @param customerName     the name of the customer
   * @param startingLocation the starting location of the customer
   * @param desiredLocation  the desired location of the customer
   */
  public Customer(String customerName, String startingLocation, String desiredLocation) {
    this.customerName = customerName;
    this.startingLocation = startingLocation;
    this.desiredLocation = desiredLocation;
  }

  /**
   * Gets the name of the customer
   *
   * @return the name of the customer
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * Gets the starting location of the customer.
   *
   * @return the starting location of the customer
   */
  public String getStartingLocation() {
    return startingLocation;
  }

  /**
   * Gets the desired location of the customer.
   *
   * @return the desired location of the customer
   */
  public String getDesiredLocation() {
    return desiredLocation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(getCustomerName(), customer.getCustomerName())
        && Objects.equals(getStartingLocation(), customer.getStartingLocation())
        && Objects.equals(getDesiredLocation(), customer.getDesiredLocation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCustomerName(), getStartingLocation(), getDesiredLocation());
  }

  @Override
  public String toString() {
    return "Customer{" +
        "customerName='" + customerName + '\'' +
        ", startingLocation='" + startingLocation + '\'' +
        ", desiredLocation='" + desiredLocation + '\'' +
        '}';
  }
}
