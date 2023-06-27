package cs3500.pa04.model.ship;

/**
 * Represents the direction of the ship
 */
public enum Direction {
  VERTICAL("VERTICAL"),
  HORIZONTAL("HORIZONTAL");

  private final String direction;

  Direction(String dir) {
    this.direction = dir;
  }

  public String getDirection() {
    return direction;
  }

}
