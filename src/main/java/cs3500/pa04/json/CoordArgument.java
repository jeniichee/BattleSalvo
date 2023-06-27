package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Coord arguments for a Json
 *
 * @param x is the x value of the coord
 * @param y is the y value of the coord
 */
public record CoordArgument(@JsonProperty("x") int x,
                            @JsonProperty("y") int y) {

  /**
   * Getter for x
   *
   * @return the x value
   */
  public int getX() {
    return this.x;
  }

  /**
   * Getter for y
   *
   * @return the y value
   */
  public int getY() {
    return this.y;
  }
}
