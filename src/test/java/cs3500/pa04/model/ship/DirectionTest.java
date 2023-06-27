package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests Direction enum
 */
class DirectionTest {

  /**
   * Tests values method
   */
  @Test
  void testValues() {
    Direction[] values = Direction.values();

    Direction[] expectedValues = {
        Direction.VERTICAL,
        Direction.HORIZONTAL,
    };

    assertArrayEquals(values, expectedValues);
  }

  /**
   * Tests valueOf method
   */
  @Test
  void testValueOf() {
    Direction vert = Direction.valueOf("VERTICAL");
    Direction horiz = Direction.valueOf("HORIZONTAL");
    assertEquals(Direction.VERTICAL, vert);
    assertEquals(Direction.HORIZONTAL, horiz);
  }
}