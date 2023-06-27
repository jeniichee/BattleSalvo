package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.ShipType;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests ConsoleView
 */
class ConsoleViewTest {

  private ConsoleView testView;

  private Board testB1;

  private Board testB2;

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    shipSelect.put(ShipType.CARRIER, 1);
    shipSelect.put(ShipType.BATTLESHIP, 2);
    shipSelect.put(ShipType.DESTROYER, 2);
    shipSelect.put(ShipType.SUBMARINE, 1);
    testB1 = new BoardImpl();
    testB1.setup(6, 6, shipSelect);
    testB2 = new BoardImpl();
    testB2.setup(6, 6, shipSelect);
  }

  /**
   * Test ReadInput
   */
  @Test
  public void testReadInput() {
    String input = "6 15\n";
    Reader in = new StringReader(input);
    StringBuilder out = new StringBuilder();
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    List<Integer> userInput = testView.readInput();

    List<Integer> expected = new ArrayList<>();
    expected.add(6);
    expected.add(15);

    assertArrayEquals(userInput.toArray(), expected.toArray());
  }

  /**
   * Test InvalidReadInputs
   */
  @Test
  public void testInvalidCharReadInput() {
    try {
      String input = "a b\n";
      Reader in = new StringReader(input);
      StringBuilder out = new StringBuilder();
      ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
      List<Integer> userInput = testView.readInput();
    } catch (Exception e) {
      System.err.println("Invalid input: " + e.getMessage());
    }
  }

  /**
   * Test the message for InvalidReadInput
   */
  @Test
  public void testInvalidReadInput() {
    StringBuilder out = new StringBuilder();
    ConsoleView testView = new ConsoleView(testB1, testB2, new StringReader(""), out);
    testView.printInvalidInputDimension();

    String expectedOutput = """
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions.
        Please remember that the height
        and width of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
           """;
    assertEquals(expectedOutput, out.toString());
  }

  /**
   * Test PrintWelcome
   */
  @Test
  public void testPrintWelcome() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    testView.printWelcome();

    assertEquals("Hello! Welcome to the OOD BattleSalvo Game!\n", out.toString());
  }

  /**
   * Test PrintInputDimension
   */
  @Test
  public void testPrintInputDimension() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    String result = """
        Please enter a valid height and width below:
        ------------------------------------------------------
           """;
    testView.printInputDimension();
    assertEquals(result, out.toString());
  }

  /**
   * Test InvalidInputDimension
   */
  @Test
  public void testPrintInvalidInputDimension() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    String result = """
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions.
        Please remember that the height
        and width of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------                                         
            """;
    testView.printInvalidInputDimension();
    assertEquals(result, out.toString());
  }

  /**
   * Test PrintFleetSelection
   */
  @Test
  public void testPrintFleetSelection() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    String result = """
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
           """;
    testView.printFleetSelection(6);
    assertEquals(result, out.toString());
  }

  /**
   * Test PrintInvalidFleet
   */
  @Test
  public void testPrintInvalidFleet() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    String result = """
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------""";
    testView.printInvalidFleet(6);
    assertEquals(result, out.toString());
  }

  /**
   * Test PrintInputShot
   */
  @Test
  public void testPrintInputShot() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    testView.printInputShot(6);

    String result = """
        Please Enter 6 Shots.
        ------------------------------------------------------------------
           """;

    assertEquals(result, out.toString());
  }

  /**
   * Test PrintB1Board
   */
  @Test
  public void testPrintB1Board() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);
    testB1.setCoord(0, 0, CoordState.HIT);

    String result = """
        Opponent Board Data:
        H 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
           """;

    testView.printB1Board(6, 6);
    assertEquals(result, out.toString());
  }

  /**
   * Test PrintManualBoard
   */
  @Test
  public void testPrintManualBoard() {
    List<Coord> carrierCoord = new ArrayList<>();
    carrierCoord.add(new Coord(0, 0));
    carrierCoord.add(new Coord(0, 1));
    carrierCoord.add(new Coord(0, 2));
    carrierCoord.add(new Coord(0, 3));
    carrierCoord.add(new Coord(0, 4));
    carrierCoord.add(new Coord(0, 5));
    testB2.setCoord(0, 0, CoordState.HIT);

    String expectedBoard = """
        Your Board:
        H 0 0 0 0 0\s
        S 0 0 0 0 0\s
        S 0 0 0 0 0\s
        S 0 0 0 0 0\s
        S 0 0 0 0 0\s
        S 0 0 0 0 0\s
           """;

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    ConsoleView testView = new ConsoleView(testB1, testB2, in, out);

    testView.printManualBoard(6, 6, carrierCoord);
    assertEquals(expectedBoard, out.toString());
  }
}