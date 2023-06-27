package cs3500.pa04.model.board;

import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents the Implementation of the Board and BoardState interface
 */
public class BoardImpl implements Board {
  private int height;

  private int width;

  private CoordState[][] grid;

  private List<Ship> ships;

  private ArrayList<Coord> enemyHitShots;

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getAliveShips() {
    int count = 0;
    for (Ship s : ships) {
      if (s.isAlive()) {
        count++;
      }
    }
    return count;
  }

  @Override
  public void updateShips(List<Coord> hitShots) {
    enemyHitShots.addAll(hitShots);
    for (Ship s : ships) {
      List<Coord> shipCords = s.getPosition();
      if (enemyHitShots.containsAll(shipCords)) {
        s.setDead();
      }
    }
  }

  @Override
  public List<Coord> getShipCoords() {
    ArrayList<Coord> shipCoords = new ArrayList<>();
    for (Ship s : ships) {
      shipCoords.addAll(s.getPosition());
    }
    return shipCoords;
  }

  @Override
  public void setCoord(int x, int y, CoordState cs) {
    grid[y][x] = cs;
  }


  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.height = height;
    this.width = width;
    grid = new CoordState[height][width];
    enemyHitShots = new ArrayList<>();
    for (CoordState[] row : grid) {
      Arrays.fill(row, CoordState.EMPTY);
    }
    ArrayList<Ship> userShips = new ArrayList<>();
    ArrayList<Coord> existingCoords = new ArrayList<>();
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipSpec = entry.getKey();
      int shipAmt = entry.getValue();
      for (int i = 0; i < shipAmt; i++) {
        Ship tempShip = new Ship(shipSpec);
        tempShip.generatePositions(height, width, existingCoords);
        userShips.add(tempShip);
      }
    }
    ships = userShips;
    return ships;
  }

  @Override
  public boolean isGameOver() {
    boolean allDead = true;
    for (Ship s : ships) {
      if (s.isAlive()) {
        allDead = false;
        break;
      }
    }
    return allDead;
  }

  @Override
  public CoordState getCoord(int x, int y) {
    return grid[y][x];
  }
}
