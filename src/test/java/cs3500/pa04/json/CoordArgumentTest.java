package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test CoordArgument functions
 */
class CoordArgumentTest {

  CoordArgument testCoordArgument;

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    testCoordArgument = new CoordArgument(1, 2);
  }

  /**
   * Test GetX
   */
  @Test
  public void testGetX() {
    assertEquals(testCoordArgument.getX(), 1);
  }

  /**
   * Test GetY
   */
  @Test
  public void testGetY() {
    assertEquals(testCoordArgument.getY(), 2);
  }
}