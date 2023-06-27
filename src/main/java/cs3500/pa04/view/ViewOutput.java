package cs3500.pa04.view;

import cs3500.pa04.model.coord.Coord;

import java.util.List;

/**
 * Represents the Output of the View
 */
public interface ViewOutput extends ViewInput {
  /**
   * Prints the welcome message of the BattleSalvo game
   */
  void printWelcome();

  /**
   * Prints the message used to input dimensions for the game
   */
  void printInputDimension();

  /**
   * Prints the message used when the user inputs an invalid dimension
   */
  void printInvalidInputDimension();

  /**
   * Prints the fleet selection message
   *
   * @param size is an Integer that represents the amount of ships
   */
  void printFleetSelection(int size);

  /**
   * Prints the message used when the user inputs an invalid amount of ships
   *
   * @param size is an Integer that represents the amount of ships
   */
  void printInvalidFleet(int size);

  /**
   * Prints the input shots message
   *
   * @param size is an Integer that represents the amount of shots
   */
  void printInputShot(int size);

  /**
   * Prints the user's current view of the enemy's board
   *
   * @param height is an Integer that represents the height of the board
   * @param width  is an Integer that represents the width of the board
   */
  void printB1Board(int height, int width);

  /**
   * Prints the user's board
   *
   * @param height     is an Integer that represents the height of the board
   * @param width      is an Integer that represents the width of the board
   * @param shipCoords is a List of Coord that represents the position of the ManualPlayer's ships
   */
  void printManualBoard(int height, int width, List<Coord> shipCoords);
}
