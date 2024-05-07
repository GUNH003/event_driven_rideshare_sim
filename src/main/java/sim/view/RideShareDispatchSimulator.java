package sim.view;

import sim.control.exception.EmptyQueueException;
import sim.control.mediator.SimDirector;

/**
 * Simulator class.
 */
public class RideShareDispatchSimulator {

  /**
   * Main method. Instantiates SimDirector and runs the simulation.
   */
  public static void main(String[] args) throws EmptyQueueException {
    SimDirector simDirector = new SimDirector();
    simDirector.run();
  }

}
