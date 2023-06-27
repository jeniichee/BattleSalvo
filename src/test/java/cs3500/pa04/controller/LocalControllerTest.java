package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.proxycontroller.MockPlayer;
import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.player.AiPlayer;
import cs3500.pa04.model.player.ManualPlayer;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.Direction;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.MockViewOutput;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * LocalController Test
 */
class LocalControllerTest {
  private LocalController controller;

  private ConsoleView testView;

  private Board testB1;

  private Board testB2;

  /**
   * Setup for the tests
   */
  @BeforeEach
  public void setUp() {
    Map<ShipType, Integer> ships = new LinkedHashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    testB1 = new BoardImpl();
    testB1.setup(6, 6, ships);
    testB2 = new BoardImpl();
    testB2.setup(6, 6, ships);
  }

  /**
   * Test SizeSelection with valid inputs
   */
  @Test
  public void testSizeSelectionValidInput() {
    MockViewOutput mockViewOutput = new MockViewOutput();
    MockPlayer mockPlayer = new MockPlayer();

    LocalController controller = new LocalController(mockPlayer, mockPlayer,
        null, null, mockViewOutput);

    int expectedX = 10;
    int expectedY = 8;

    mockViewOutput.setInputValues(expectedY + " " + expectedX);
    Coord result = controller.sizeSelection();

    assertEquals(expectedX, result.getCoordX());
    assertEquals(expectedY, result.getCoordY());

    assertTrue(mockViewOutput.printInputDimensionCalled);
    assertFalse(mockViewOutput.printInvalidInputDimensionCalled);
  }

  /**
   * Test SizeSelection with invalid inputs
   */
  @Test
  public void testSizeSelectionInvalidInput() {
    MockViewOutput mockViewOutput = new MockViewOutput();
    MockPlayer mockPlayer = new MockPlayer();

    LocalController controller = new LocalController(mockPlayer, mockPlayer,
        null, null, mockViewOutput);

    int expectedX1 = 0;
    int expectedY1 = 8;

    mockViewOutput.setInputValues(expectedY1 + " " + expectedX1);
    assertThrows(StackOverflowError.class, controller::sizeSelection);

    int expectedX2 = 16;
    int expectedY2 = 8;

    mockViewOutput.setInputValues(expectedY2 + " " + expectedX2);
    assertThrows(StackOverflowError.class, controller::sizeSelection);

    int expectedX3 = 8;
    int expectedY3 = 0;

    mockViewOutput.setInputValues(expectedY3 + " " + expectedX3);
    assertThrows(StackOverflowError.class, controller::sizeSelection);

    int expectedX4 = 8;
    int expectedY4 = 16;

    mockViewOutput.setInputValues(expectedY4 + " " + expectedX4);
    assertThrows(StackOverflowError.class, controller::sizeSelection);
  }

  /**
   * Test ShipSelection with valid inputs
   */
  @Test
  void testValidShipSelectionSetup() {
    int x = 10;
    int y = 10;

    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("1 1 1 1\n");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    LocalController localController = new LocalController(new AiPlayer(testB1),
        new ManualPlayer(testB2, testView), testB1, testB2, testView);

    Map<ShipType, Integer> shipSelect = localController.shipSelectionSetup(x, y);

    assertNotNull(shipSelect);
    assertEquals(1, shipSelect.get(ShipType.CARRIER));
    assertEquals(1, shipSelect.get(ShipType.BATTLESHIP));
    assertEquals(1, shipSelect.get(ShipType.DESTROYER));
    assertEquals(1, shipSelect.get(ShipType.SUBMARINE));
  }

  /**
   * Test ManualShots with valid input
   */
  @Test
  public void testManualShots() {
    int height = 6;
    int width = 6;

    Map<ShipType, Integer> ships = new LinkedHashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    List<Ship> manualShips = testB2.setup(height, width, ships);

    List<Coord> previousShots = new ArrayList<>();

    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("0 0 0 1 0 2 0 3\n");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    LocalController localController = new LocalController(new AiPlayer(testB1),
        new ManualPlayer(testB2, testView), testB1, testB2, testView);

    List<Coord> shots = localController.manualShots(height, width, manualShips, previousShots);
    assertNotNull(shots);
    assertEquals(4, shots.size());
  }

  /**
   * Tests ManualShots with other cases
   */
  @Test
  public void testOtherCasesManualShots() {
    Map<ShipType, Integer> ships = new LinkedHashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);

    int height = 6;
    int width = 6;
    List<Ship> manualShips = testB1.setup(height, width, ships);
    manualShips.get(0).setDead();

    testB1.setCoord(0, 0, CoordState.HIT);
    testB1.setCoord(0, 1, CoordState.MISSED);

    List<Coord> previousShots = new ArrayList<>();
    previousShots.add(new Coord(0, 0));
    previousShots.add(new Coord(0, 1));

    StringBuilder out1 = new StringBuilder();
    StringReader in1 = new StringReader("0 0 0 1 0 2 0 3\n");
    ConsoleView testView1 = new ConsoleView(testB1, testB2, in1, out1);
    LocalController localController1 = new LocalController(new ManualPlayer(testB1, testView1),
        new AiPlayer(testB2), testB1, testB2, testView1);

    assertThrows(NoSuchElementException.class, () ->
        localController1.manualShots(height, width, manualShips, previousShots));
  }

  /**
   * Tests ManualShots with invalid input
   */
  @Test
  public void testInvalidManualShots() {
    Map<ShipType, Integer> ships = new LinkedHashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);

    int height = 6;
    int width = 6;
    List<Ship> manualShips = testB1.setup(height, width, ships);
    manualShips.get(0).setDead();

    testB1.setCoord(0, 0, CoordState.HIT);
    testB1.setCoord(0, 1, CoordState.MISSED);

    List<Coord> previousShots = new ArrayList<>();
    previousShots.add(new Coord(0, 0));
    previousShots.add(new Coord(0, 1));

    StringBuilder out1 = new StringBuilder();
    StringReader in1 = new StringReader("-1 0 0 1 0 2 0 3\n");
    ConsoleView testView1 = new ConsoleView(testB1, testB2, in1, out1);
    LocalController localController1 = new LocalController(new ManualPlayer(testB1, testView1),
        new AiPlayer(testB2), testB1, testB2, testView1);

    assertThrows(NoSuchElementException.class, () ->
        localController1.manualShots(height, width, manualShips, previousShots));
  }

  /**
   * Test GetAllShipCoords
   */
  @Test
  void getAllShipCoords() {
    ArrayList<Coord> carrierPos = new ArrayList<>();
    carrierPos.add(new Coord(0, 0));
    carrierPos.add(new Coord(0, 1));
    carrierPos.add(new Coord(0, 2));
    carrierPos.add(new Coord(0, 3));
    carrierPos.add(new Coord(0, 4));
    carrierPos.add(new Coord(0, 5));

    List<Ship> mockShips = new ArrayList<>();
    mockShips.add(new Ship(ShipType.CARRIER, carrierPos, Direction.VERTICAL));

    ArrayList<Coord> battleshipPos = new ArrayList<>();
    battleshipPos.add(new Coord(2, 0));
    battleshipPos.add(new Coord(3, 0));
    battleshipPos.add(new Coord(4, 0));
    battleshipPos.add(new Coord(5, 0));
    battleshipPos.add(new Coord(6, 0));
    mockShips.add(new Ship(ShipType.BATTLESHIP, battleshipPos, Direction.HORIZONTAL));

    ArrayList<Coord> destroyerPos = new ArrayList<>();
    destroyerPos.add(new Coord(10, 0));
    destroyerPos.add(new Coord(10, 1));
    destroyerPos.add(new Coord(10, 2));
    destroyerPos.add(new Coord(10, 3));
    mockShips.add(new Ship(ShipType.DESTROYER, destroyerPos, Direction.VERTICAL));

    ArrayList<Coord> submarinePos = new ArrayList<>();
    submarinePos.add(new Coord(6, 6));
    submarinePos.add(new Coord(7, 6));
    submarinePos.add(new Coord(8, 6));
    mockShips.add(new Ship(ShipType.SUBMARINE, submarinePos, Direction.HORIZONTAL));

    LocalController localController = new LocalController(new AiPlayer(testB1),
        new ManualPlayer(testB2, testView), testB1, testB2, testView);
    List<Coord> allShipCoords = localController.getAllShipCoords(mockShips);

    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(0, 0));
    expectedCoords.add(new Coord(0, 1));
    expectedCoords.add(new Coord(0, 2));
    expectedCoords.add(new Coord(0, 3));
    expectedCoords.add(new Coord(0, 4));
    expectedCoords.add(new Coord(0, 5));
    expectedCoords.add(new Coord(2, 0));
    expectedCoords.add(new Coord(3, 0));
    expectedCoords.add(new Coord(4, 0));
    expectedCoords.add(new Coord(5, 0));
    expectedCoords.add(new Coord(6, 0));
    expectedCoords.add(new Coord(10, 0));
    expectedCoords.add(new Coord(10, 1));
    expectedCoords.add(new Coord(10, 2));
    expectedCoords.add(new Coord(10, 3));
    expectedCoords.add(new Coord(6, 6));
    expectedCoords.add(new Coord(7, 6));
    expectedCoords.add(new Coord(8, 6));
    assertEquals(expectedCoords, allShipCoords);
  }

  /**
   * Tests for SetUserMissedCoords
   */
  @Test
  void setUserMissedCoords() {
    Player player1 = new ManualPlayer(testB2, testView);
    Player player2 = new AiPlayer(testB1);
    LocalController localController =
        new LocalController(player2, player1, testB1, testB2, testView);

    List<Coord> allShots = new ArrayList<>();
    allShots.add(new Coord(0, 0));
    allShots.add(new Coord(1, 1));
    allShots.add(new Coord(2, 2));

    List<Coord> hitShots = new ArrayList<>();
    hitShots.add(new Coord(0, 0));
    hitShots.add(new Coord(2, 2));

    localController.setUserMissedCoords(allShots, hitShots);
    assertEquals(CoordState.MISSED, testB1.getCoord(1, 1));
  }

  /**
   * Test SetAiMissedCoords
   */
  @Test
  void setAiMissedCoord() {
    Player player1 = new ManualPlayer(testB2, testView);
    Player player2 = new AiPlayer(testB1);
    LocalController localController =
        new LocalController(player2, player1, testB1, testB2, testView);

    List<Coord> allShots = new ArrayList<>();
    allShots.add(new Coord(0, 0));
    allShots.add(new Coord(1, 1));
    allShots.add(new Coord(2, 2));

    List<Coord> hitShots = new ArrayList<>();
    allShots.add(new Coord(0, 0));

    localController.setAiMissedCoord(allShots, hitShots);
    assertEquals(CoordState.MISSED, testB2.getCoord(2, 2));
  }

  /**
   * Test for Run
   */
  @Test
  public void testRun() {
    return;
  }
}