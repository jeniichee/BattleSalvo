package cs3500.pa04.model.coord;

/**
 * Represents a Coordinate point on a board
 */
public class Coord {
  private final int coordX;
  private final int coordY;

  /**
   * Constructor for Coord
   *
   * @param x is an int that represent the x-position
   * @param y is an int that represents the y-position
   */
  public Coord(int x, int y) {
    coordX = x;
    coordY = y;
  }

  /**
   * Getter method for the xCoord
   *
   * @return an int that represents the xCoord
   */
  public int getCoordX() {
    return coordX;
  }

  /**
   * Getter for the yCoord
   *
   * @return an int that represents the yCoord
   */
  public int getCoordY() {
    return coordY;
  }

  /**
   * Override the == operator for a Coord. A Coord is equal if the xCoord and the yCoord are the
   * same
   *
   * @param obj is some other obj
   * @return a boolean that represent if the Coord is equal to the obj
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else {
      Coord otherCoord = (Coord) obj;
      return this.coordX == otherCoord.coordX && this.coordY == otherCoord.coordY;
    }
  }
}
