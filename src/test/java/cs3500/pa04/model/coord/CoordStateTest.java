package cs3500.pa04.model.coord;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests CoordState Enum
 */
class CoordStateTest {
  /**
   * Tests getSymbol method
   */
  @Test
  void getSymbol() {
    assertEquals(CoordState.HIT.getSymbol(), 'H');
    assertEquals(CoordState.MISSED.getSymbol(), 'M');
    assertEquals(CoordState.EMPTY.getSymbol(), '0');
    assertEquals(CoordState.SHIP.getSymbol(), 'S');
  }

  /**
   * Tests values method
   */
  @Test
  void testValues() {
    CoordState[] values = CoordState.values();

    CoordState[] expectedValues =
        {CoordState.HIT, CoordState.MISSED, CoordState.EMPTY, CoordState.SHIP};

    assertArrayEquals(values, expectedValues);
  }

  /**
   * Tests valueOf method
   */
  @Test
  void testValueOf() {
    CoordState hit = CoordState.valueOf("HIT");
    CoordState miss = CoordState.valueOf("MISSED");
    CoordState empty = CoordState.valueOf("EMPTY");
    CoordState ship = CoordState.valueOf("SHIP");

    assertEquals(CoordState.HIT, hit);
    assertEquals(CoordState.MISSED, miss);
    assertEquals(CoordState.EMPTY, empty);
    assertEquals(CoordState.SHIP, ship);
  }
}