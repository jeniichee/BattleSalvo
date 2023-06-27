package cs3500.pa04.view;

import java.util.List;

/**
 * Represents an interface to output as well as receive input
 */
public interface ViewInput {
  /**
   * ReadInput is used to obtain the user input
   *
   * @return a List of Integers that will be parsed to obtain the user inputs
   */
  List<Integer> readInput();
}
