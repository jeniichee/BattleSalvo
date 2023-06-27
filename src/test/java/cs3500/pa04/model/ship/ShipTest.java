package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.coord.Coord;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Ship
 */
class ShipTest {
  Ship testShip;

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    testShip = new Ship(ShipType.CARRIER);
  }

  /**
   * Tests IsAlive and SetDead
   */
  @Test
  public void testIsAliveAndSetDead() {
    assertTrue(testShip.isAlive());
    testShip.setDead();
    assertFalse(testShip.isAlive());
  }

  /**
   * Tests GetPosition and GeneratePositions
   */
  @Test
  void testGetPositionAndGeneratePositions() {
    testShip.generatePositions(6, 7, new ArrayList<>());
    List<Coord> hasCoords = testShip.getPosition();
    assertEquals(hasCoords.size(), 6);
    for (Coord c : hasCoords) {
      assertTrue(c.getCoordX() < 7);
      assertTrue(c.getCoordX() >= 0);
      assertTrue(c.getCoordY() < 6);
      assertTrue(c.getCoordY() >= 0);
    }
  }
}