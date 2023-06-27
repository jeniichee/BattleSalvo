package cs3500.pa04.model.player;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.proxycontroller.MockPlayer;
import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.view.MockViewInput;
import cs3500.pa04.view.ViewInput;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for ManualPlayer
 */
class ManualPlayerTest {

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
    testPlayer = new ManualPlayer(testBoard, testView);
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    shipSelect.put(ShipType.CARRIER, 1);
    shipSelect.put(ShipType.BATTLESHIP, 2);
    shipSelect.put(ShipType.DESTROYER, 2);
    shipSelect.put(ShipType.SUBMARINE, 1);
    setupShips = testPlayer.setup(6, 7, shipSelect);
  }

  /**
   * Test TakeShotsConstructor
   */
  @Test
  public void testTakeShotsConstructor() {
    testBoard.setCoord(0, 0, CoordState.HIT);
    testBoard.setCoord(0, 1, CoordState.MISSED);
    String input = """
        0 2
        0 3
        0 4
        0 5
        1 0
        1 1
           """;
    StringBuilder out = new StringBuilder();
    MockViewInput testView = new MockViewInput(testBoard, new BoardImpl(), input, out);

    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(0, 2));
    expected.add(new Coord(0, 3));
    expected.add(new Coord(0, 4));
    expected.add(new Coord(0, 5));
    expected.add(new Coord(1, 0));
    expected.add(new Coord(1, 1));
    List<Coord> userInput = testView.readCoord();
    assertArrayEquals(userInput.toArray(), expected.toArray());
  }

  /**
   * Test TakeShots
   */
  @Test
  void testTakeShots() {
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    shipSelect.put(ShipType.CARRIER, 1);
    shipSelect.put(ShipType.BATTLESHIP, 2);
    shipSelect.put(ShipType.DESTROYER, 2);
    shipSelect.put(ShipType.SUBMARINE, 1);
    BoardImpl mockBoard = new BoardImpl();
    mockBoard.setup(6, 6, shipSelect);
    BoardImpl mockBoard2 = new BoardImpl();
    mockBoard2.setup(6, 6, shipSelect);

    mockBoard2.setCoord(0, 0, CoordState.HIT);
    mockBoard.setCoord(0, 0, CoordState.MISSED);
    mockBoard.setCoord(1, 1, CoordState.HIT);

    StringReader sr = new StringReader("1 2 0 2 5 4 6 3");
    StringBuilder sb = new StringBuilder();
    MockViewInput consoleView = new MockViewInput(mockBoard, mockBoard2, sr, sb);
    MockPlayer manualPlayer = new MockPlayer(mockBoard, consoleView);

    Coord coord = new Coord(1, 2);
    Coord coord1 = new Coord(0, 2);
    Coord coord2 = new Coord(5, 4);
    Coord coord3 = new Coord(6, 3);

    List<Coord> expectedShots = new ArrayList<>();
    expectedShots.add(coord);
    expectedShots.add(coord1);
    expectedShots.add(coord2);
    expectedShots.add(coord3);

    List<Coord> shots = manualPlayer.takeShots();

    assertEquals(expectedShots, shots);
  }

}
