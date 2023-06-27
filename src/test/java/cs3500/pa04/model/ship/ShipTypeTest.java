package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests ShipType Enum
 */
class ShipTypeTest {

  /**
   * Tests getSize method
   */
  @Test
  void getSize() {
    assertEquals(ShipType.CARRIER.getSize(), 6);
    assertEquals(ShipType.BATTLESHIP.getSize(), 5);
    assertEquals(ShipType.DESTROYER.getSize(), 4);
    assertEquals(ShipType.SUBMARINE.getSize(), 3);
  }

  /**
   * Tests values method
   */
  @Test
  void testValues() {
    ShipType[] values = ShipType.values();

    ShipType[] expectedValues = {
        ShipType.CARRIER,
        ShipType.BATTLESHIP,
        ShipType.DESTROYER,
        ShipType.SUBMARINE
    };

    assertArrayEquals(values, expectedValues);
  }

  /**
   * Tests valueOf method
   */
  @Test
  void testValueOf() {
    ShipType carr = ShipType.valueOf("CARRIER");
    ShipType bat = ShipType.valueOf("BATTLESHIP");
    ShipType dest = ShipType.valueOf("DESTROYER");
    final ShipType sub = ShipType.valueOf("SUBMARINE");

    assertEquals(ShipType.CARRIER, carr);
    assertEquals(ShipType.BATTLESHIP, bat);
    assertEquals(ShipType.DESTROYER, dest);
    assertEquals(ShipType.SUBMARINE, sub);
  }
}