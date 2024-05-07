package sim.model.customer;

/**
 * BasicCustomer class, representing a basic customer.
 */
public final class BasicCustomer extends Customer {

  /**
   * Constructor for option1.model.customer.Customer class.
   *
   * @param customerName     the name of the customer
   * @param startingLocation the starting location of the customer
   * @param desiredLocation  the desired location of the customer
   */
  public BasicCustomer(String customerName, String startingLocation, String desiredLocation) {
    super(customerName, startingLocation, desiredLocation);
  }

  @Override
  public String toString() {
    return "BasicCustomer{} " + super.toString();
  }
}
