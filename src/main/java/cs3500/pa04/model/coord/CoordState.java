package cs3500.pa04.model.coord;


/**
 * Represents a CoordState
 */
public enum CoordState {
  HIT('H'),
  MISSED('M'),
  EMPTY('0'),
  SHIP('S');


  private final char symbol;

  /**
   * Constructor for CoordState enum
   *
   * @param c is a char that is used to represent in CoordState
   */
  CoordState(char c) {
    symbol = c;
  }

  /**
   * GetSymbol is a method used to return the symbol linked to the CoordState
   *
   * @return a Char that represents the CoordState symbol
   */
  public char getSymbol() {
    return symbol;
  }


}
