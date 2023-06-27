package cs3500.pa04.model.board;


import cs3500.pa04.model.coord.CoordState;

/**
 * BoardState represents the current state of a board
 */
public interface BoardState {
  /**
   * IsGameOver is a method used to determine if the game is over, which occurs when all the ships
   * are sunk
   *
   * @return a boolean that is true if the game is over and false if it isn't
   */
  boolean isGameOver();

  /**
   * GetCoord is a method used to get the CoordState at a specific x, y coordinate
   *
   * @param x is an Integer representing the x coordinate
   * @param y is an Integer representing the y coordinate
   * @return the CoordState at the given x, y coordinate
   */
  CoordState getCoord(int x, int y);

  /**
   * SetCoord is a method used to set a coordinate on the board to a specific CoordState
   *
   * @param x  is an Integer representing the x coordinate
   * @param y  is an Integer representing the y coordinate
   * @param cs is a CoordState representing the new coordState
   */
  void setCoord(int x, int y, CoordState cs);
}
