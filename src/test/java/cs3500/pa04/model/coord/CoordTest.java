package cs3500.pa04.model.coord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests Coord class method
 */
class CoordTest {
  private Coord testCoord;

  /**
   * Sets up Coord
   */
  @BeforeEach
  public void setup() {
    testCoord = new Coord(1, 2);
  }

  /**
   * Tests getCoordX method
   */
  @Test
  void testGetCoordX() {
    assertEquals(testCoord.getCoordX(), 1);
  }

  /**
   * Tests getCoordY method
   */
  @Test
  void testGetCoordY() {
    assertEquals(testCoord.getCoordY(), 2);
  }

  /**
   * Tests equals override method
   */
  @Test
  void testEquals() {
    Coord sameCoord = new Coord(1, 2);
    Coord notSameCoord = new Coord(3, 4);
    assertEquals(testCoord, sameCoord);
    assertNotEquals(testCoord, notSameCoord);
    assertNotEquals(testCoord, null);
    assertEquals(testCoord, testCoord);
  }
}