package cs3500.pa04.view;


import cs3500.pa04.model.board.BoardState;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the implementation of ViewInput and ViewOutput
 */
public class ConsoleView implements ViewOutput {
  private final BoardState b1;
  private final BoardState b2;
  private final Appendable out;
  private final Readable in;

  /**
   * Constructor for ConsoleView
   *
   * @param b1  a BoardState that represents the Board of one player
   * @param b2  a BoardState that represents the Board of the other player
   * @param in  the Readable input source
   * @param out the Appendable output destination
   */
  public ConsoleView(BoardState b1, BoardState b2, Readable in, Appendable out) {
    this.b1 = b1;
    this.b2 = b2;
    this.in = in;
    this.out = out;
  }

  public ConsoleView(BoardState b1, BoardState b2) {
    this(b1, b2, new InputStreamReader(System.in), System.out);
  }

  @Override
  public List<Integer> readInput() {
    ArrayList<Integer> lineInts = new ArrayList<>();
    Scanner s = new Scanner(this.in);
    String inputLine = s.nextLine();

    String[] numbers = inputLine.split(" ");
    for (String n : numbers) {
      try {
        int value = Integer.parseInt(n);
        lineInts.add(value);
      } catch (NumberFormatException e) {
        System.err.println("Invalid input: " + e.getMessage());
      }
    }
    return lineInts;
  }


  @Override
  public void printWelcome() {
    append("Hello! Welcome to the OOD BattleSalvo Game!\n");
  }

  @Override
  public void printInputDimension() {
    append("""
        Please enter a valid height and width below:
        ------------------------------------------------------
        """);
  }

  @Override
  public void printInvalidInputDimension() {
    append("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions.
        Please remember that the height
        and width of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """);
  }

  @Override
  public void printFleetSelection(int size) {
    append("Please enter your fleet in the order [Carrier, Battleship, Destroyer, "
        + "Submarine].\n" + "Remember, your fleet may not exceed size " + size + ".\n");
    append("------------------------------------------------------\n");
  }

  @Override
  public void printInvalidFleet(int size) {
    append("------------------------------------------------------\n");
    append("Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + size + ".\n");
    append("------------------------------------------------------");
  }

  @Override
  public void printInputShot(int shotAmt) {
    append("Please Enter " + shotAmt + " Shots.\n");
    append("------------------------------------------------------------------\n");
  }

  @Override
  public void printB1Board(int height, int width) {
    append("Opponent Board Data:\n");
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        append(b1.getCoord(j, i).getSymbol() + " ");
      }
      append("\n");
    }
  }

  @Override
  public void printManualBoard(int height, int width, List<Coord> shipCoords) {
    append("Your Board:\n");
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coord tempCoord = new Coord(j, i);
        if (shipCoords.contains(tempCoord) && b2.getCoord(j, i) != CoordState.HIT) {
          append(CoordState.SHIP.getSymbol() + " ");
        } else {
          append(b2.getCoord(j, i).getSymbol() + " ");
        }
      }
      append("\n");
    }
  }

  private void append(CharSequence csq) {
    try {
      out.append(csq);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
