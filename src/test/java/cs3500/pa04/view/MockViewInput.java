package cs3500.pa04.view;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of the ViewInput interface.
 * It allows predefined input values to be provided for testing purposes.
 */
public class MockViewInput implements ViewInput {
  private List<List<Integer>> inputValues;
  private int currentIndex;
  private Board testBoard;
  private BoardImpl board;
  private Appendable out;
  private Readable readable;
  private String in;

  /**
   * Constructor for MockViewInput with the provided test board, board, readable input, and output.
   *
   * @param testBoard the test board to be used
   * @param board the board to be used
   * @param in  the string containing predefined values
   * @param out the output to be used
   */
  public MockViewInput(Board testBoard, BoardImpl board, String in, Appendable out) {
    this.testBoard = testBoard;
    this.board = board;
    this.in = in;
    this.out = out;
  }

  /**
   * Constructs a MockViewInput object with the provided
   * test board, board, readable input, and output.
   *
   * @param testBoard the test board to be used
   * @param board the board to be used
   * @param readable the readable input containing predefined values
   * @param out the output to be used
   */
  public MockViewInput(BoardImpl testBoard, BoardImpl board, Readable readable, Appendable out) {
    this.readable = new InputStreamReader(System.in);
    this.currentIndex = 0;
  }

  /**
   * Reads and parses the input coordinates from the predefined input string.
   *
   * @return a list of Coord objects representing the input coordinates
   */
  public List<Coord> readCoord() {
    List<Coord> inputCoords = new ArrayList<>();
    String[] lines = in.trim().split("\\r?\\n");
    for (String line : lines) {
      String[] tokens = line.split("\\s+");
      int x = Integer.parseInt(tokens[0]);
      int y = Integer.parseInt(tokens[1]);
      inputCoords.add(new Coord(x, y));
    }
    return inputCoords;
  }

  @Override
  public List<Integer> readInput() {
    List<Integer> values = inputValues.get(currentIndex);
    currentIndex++;
    return values;
  }

}
