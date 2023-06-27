package cs3500.pa04.view;

import cs3500.pa04.model.coord.Coord;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of the ViewOutput interface.
 * Provides methods for setting predefined input values and tracking method
 * invocations for testing purposes.
 */
public class MockViewOutput implements ViewOutput {
  private List<Integer> inputValues;
  public boolean printInputDimensionCalled;
  public boolean printInvalidInputDimensionCalled;

  /**
   * Constructor for MockViewOutput
   */
  public MockViewOutput() {
  }

  /**
   * Sets the input values for reading input.
   *
   * @param inputValues a string containing space-separated input values
   */
  public void setInputValues(String inputValues) {
    this.inputValues = new ArrayList<>();
    String[] numbers = inputValues.split(" ");
    for (String n : numbers) {
      try {
        int value = Integer.parseInt(n);
        this.inputValues.add(value);
      } catch (NumberFormatException e) {
        System.err.println("Invalid input: " + e.getMessage());
      }
    }
  }

  /**
   * Returns the input values.
   *
   * @return a list of input values
   */
  public List<Integer> readInput() {
    return inputValues;
  }

  @Override
  public void printWelcome() {

  }

  /**
   * Marks that the printInputDimension method has been called.
   */
  public void printInputDimension() {
    printInputDimensionCalled = true;
  }

  /**
   * Marks that the printInvalidInputDimension method has been called.
   */
  public void printInvalidInputDimension() {
  }

  @Override
  public void printFleetSelection(int size) {

  }

  @Override
  public void printInvalidFleet(int size) {

  }

  @Override
  public void printInputShot(int size) {

  }

  @Override
  public void printB1Board(int height, int width) {

  }

  @Override
  public void printManualBoard(int height, int width, List<Coord> shipCoords) {

  }
}
