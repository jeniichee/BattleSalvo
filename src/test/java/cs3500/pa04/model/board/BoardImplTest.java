package cs3500.pa04.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for BoardImpl
 */
class BoardImplTest {

  Board testBoard;
  List<Ship> testShips;

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    testBoard = new BoardImpl();
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    shipSelect.put(ShipType.CARRIER, 1);
    shipSelect.put(ShipType.BATTLESHIP, 2);
    shipSelect.put(ShipType.DESTROYER, 2);
    shipSelect.put(ShipType.SUBMARINE, 1);
    testShips = testBoard.setup(6, 7, shipSelect);
  }

  /**
   * Tests GetHeight and GetWidth
   */
  @Test
  public void testGetHeightAndWidth() {
    assertEquals(testBoard.getHeight(), 6);
    assertEquals(testBoard.getWidth(), 7);
  }

  /**
   * Tests GetAliveShips
   */
  @Test
  public void testGetAliveShips() {
    assertEquals(testBoard.getAliveShips(), 6);
    testShips.get(0).setDead();
    assertEquals(testBoard.getAliveShips(), 5);
  }

  /**
   * Tests UpdateShips
   */
  @Test
  public void testUpdateShips() {
    testBoard.updateShips(testBoard.getShipCoords());
    assertEquals(testBoard.getAliveShips(), 0);
  }

  /**
   * Tests GetShipCoords
   */
  @Test
  public void testGetShipCoords() {
    List<Coord> shipCoords = testBoard.getShipCoords();
    assertEquals(shipCoords.size(), 27);
  }

  /**
   * Tests SetCoord and GetCoord
   */
  @Test
  public void testSetAndGetCoord() {
    assertEquals(testBoard.getCoord(0, 0), CoordState.EMPTY);
    testBoard.setCoord(0, 0, CoordState.HIT);
    assertEquals(testBoard.getCoord(0, 0), CoordState.HIT);
  }

  /**
   * Tests IsGameOver
   */
  @Test
  public void testIsGameOver() {
    assertFalse(testBoard.isGameOver());
    for (Ship s : testShips) {
      s.setDead();
    }
    assertTrue(testBoard.isGameOver());
  }
}