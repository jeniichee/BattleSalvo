package cs3500.pa04.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ViewInput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for AbstractPlayer
 */
class AbstractPlayerTest {

  private Player testPlayer;

  private Board testBoard;

  private ViewInput testView;

  List<Ship> setupShips;

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    testBoard = new BoardImpl();
    testView = new ConsoleView(testBoard, new BoardImpl());
    testPlayer = new ManualPlayer(testBoard, testView);
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    shipSelect.put(ShipType.CARRIER, 1);
    shipSelect.put(ShipType.BATTLESHIP, 2);
    shipSelect.put(ShipType.DESTROYER, 2);
    shipSelect.put(ShipType.SUBMARINE, 1);
    setupShips = testPlayer.setup(6, 7, shipSelect);
  }

  /**
   * Tests Name
   */
  @Test
  public void testName() {
    assertEquals(testPlayer.name(), "name");
  }

  /**
   * Tests Setup
   */
  @Test
  public void testSetup() {
    assertEquals(setupShips.size(), 6);
    for (Ship s : setupShips) {
      assertTrue(s.getPosition().size() >= 3);
    }
  }

  /**
   * Tests ReportDamage
   */
  @Test
  public void testReportDamage() {
    List<Coord> shipCoords = testBoard.getShipCoords();
    List<Coord> testHitsAndMisses = new ArrayList<>();
    testHitsAndMisses.add(shipCoords.get(0));
    testHitsAndMisses.add(shipCoords.get(1));
    testHitsAndMisses.add(shipCoords.get(2));
    testHitsAndMisses.add(new Coord(7, 7));
    testHitsAndMisses.add(new Coord(8, 8));

    List<Coord> reportedHits = testPlayer.reportDamage(testHitsAndMisses);
    assertEquals(reportedHits.size(), 3);
    assertEquals(reportedHits.get(0), shipCoords.get(0));
    assertEquals(reportedHits.get(1), shipCoords.get(1));
    assertEquals(reportedHits.get(2), shipCoords.get(2));
  }

  /**
   * Tests SuccessfulHits
   */
  @Test
  public void testSuccessfulHits() {
    List<Coord> exampleOpponentHits = new ArrayList<>();
    exampleOpponentHits.add(new Coord(0, 0));
    exampleOpponentHits.add(new Coord(0, 1));
    exampleOpponentHits.add(new Coord(0, 2));
    testPlayer.successfulHits(exampleOpponentHits);
    assertEquals(testBoard.getCoord(0, 0), CoordState.HIT);
    assertEquals(testBoard.getCoord(0, 1), CoordState.HIT);
    assertEquals(testBoard.getCoord(0, 2), CoordState.HIT);
  }

  /**
   * Tests EndGame
   */
  @Test
  public void testEndGame() {
    testPlayer.endGame(GameResult.DRAW, "null");
  }
}