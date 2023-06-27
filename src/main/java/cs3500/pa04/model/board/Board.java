package cs3500.pa04.model.board;

import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.util.List;
import java.util.Map;

/**
 * Represents a Board that extends a BoardState
 */
public interface Board extends BoardState {
  /**
   * Getter for the height of the board
   *
   * @return an Integer representing the height of the board
   */
  int getHeight();

  /**
   * Getter for the width of the board
   *
   * @return an Integer representing the width of the board
   */
  int getWidth();

  /**
   * GetAliveShips is a method that returns the amount of alive ships on the board
   *
   * @return an Integer that represents the amount of alive ships on the board
   */
  int getAliveShips();

  void updateShips(List<Coord> shots);

  /**
   * Returns all the Coords of the ships on the board
   *
   * @return a List of Coords that represent all the ships on the board
   */
  List<Coord> getShipCoords();

  /**
   * Setup is a method that is used to set up the Board with the height and width dimensions
   * as well as adding ships to the board
   *
   * @param height         is an Integer that is the height of the board
   * @param width          is an Integer that is the width of the board
   * @param specifications is a Map of ShipTypes and the amount of each ShipType on the board
   * @return a List of initialized ships with positions
   */
  List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications);
}
