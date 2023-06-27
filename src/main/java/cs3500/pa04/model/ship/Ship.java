package cs3500.pa04.model.ship;

import cs3500.pa04.model.coord.Coord;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a Ship in the BattleSalvoGame
 */
public class Ship {
  private final ShipType type;
  private ArrayList<Coord> position;
  private Direction shipDirection;
  private boolean isAlive;

  /**
   * Constructor for a Ship
   *
   * @param type is a ShipType that represents the ship type
   */
  public Ship(ShipType type) {
    this.type = type;
    this.isAlive = true;
  }

  /**
   * for testing purposes
   */
  public Ship(ShipType type, ArrayList<Coord> position, Direction shipDir) {
    this.type = type;
    this.position = position;
    this.shipDirection = shipDir;
  }

  /**
   * IsAlive determines if the ship is still alive
   *
   * @return a Boolean that represents if the ship is still alive (true = alive, false = dead)
   */
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * Sets the isAlive status of the ship to false
   */
  public void setDead() {
    isAlive = false;
  }

  /**
   * GetPosition returns all the Coords that represent the ship
   *
   * @return an ArrayList of Coords that represent the ships position
   */
  public ArrayList<Coord> getPosition() {
    return position;
  }

  /**
   * Helper method used to create a Coord
   *
   * @param orientationNum is an Integer that is used to determine the direction of the ship
   * @param startCoordX    is an Integer that represents the starting x coordinate
   * @param startCoordY    is an Integer that represents the starting y coordinate
   * @param i              represents an Integer used to determine the next Coord that
   *                       is needed to make the ship
   * @return a Coord that represents one of the position components of the ship
   */
  private Coord createCoord(int orientationNum, int startCoordX, int startCoordY, int i) {
    if (orientationNum == 0) {
      return new Coord(startCoordX - i, startCoordY);
    } else {
      return new Coord(startCoordX, startCoordY - i);
    }
  }

  /**
   * GeneratePositions is used to randomly generate Coords for the Ship on the board
   *
   * @param y              is an Integer representing the height of the board
   * @param x              is an Integer representing the width of the board
   * @param existingCoords is a List of Coords that represents the Coords that already exist
   */
  public void generatePositions(int y, int x, ArrayList<Coord> existingCoords) {
    ArrayList<Coord> tempCoords = new ArrayList<>();
    Random r = new Random(System.currentTimeMillis());
    int sizeBuffer = type.getSize();
    boolean hasOverlap;
    int startCoordX;
    int startCoordY;
    do {
      int orientationNum = r.nextInt(2);
      if (orientationNum == 0) {
        shipDirection = Direction.HORIZONTAL;
        startCoordX = r.nextInt(x + 1 - sizeBuffer) - 1 + sizeBuffer;
        startCoordY = r.nextInt(y);
      } else {
        shipDirection = Direction.VERTICAL;
        startCoordX = r.nextInt(x);
        startCoordY = r.nextInt(y + 1 - sizeBuffer) - 1 + sizeBuffer;
      }
      tempCoords.clear();
      for (int i = 0; i < sizeBuffer; i++) {
        tempCoords.add(0, createCoord(orientationNum, startCoordX, startCoordY, i));
      }
      hasOverlap = false;
      for (Coord tempCoord : tempCoords) {
        if (existingCoords.contains(tempCoord)) {
          hasOverlap = true;
          break;
        }
      }
    } while (hasOverlap);
    existingCoords.addAll(tempCoords);
    position = tempCoords;
  }

  /**
   * Getter for ShipType
   *
   * @return the type of the ship
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Getter for ShipDirection
   *
   * @return the direction of the ship
   */
  public Direction getShipDirection() {
    return shipDirection;
  }

}
