package cs3500.pa04.model.ship;

/**
 * Represents a ship's type
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  /**
   * Constructor for ShipType
   *
   * @param s is an int that represents the size of the ship
   */
  ShipType(int s) {
    size = s;
  }

  /**
   * Getter method for size
   *
   * @return an int that represents the size
   */
  public int getSize() {
    return size;
  }

}
