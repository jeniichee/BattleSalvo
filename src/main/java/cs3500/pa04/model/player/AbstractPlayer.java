package cs3500.pa04.model.player;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AbstractPlayer represents a general abstract implementation of a Player
 */
public abstract class AbstractPlayer implements Player {
  protected final Board board;

  /**
   * AbstractPlayer constructor
   *
   * @param board is a Board that the Players update and use
   */
  AbstractPlayer(Board board) {
    this.board = board;
  }

  @Override
  public String name() {
    return "name";
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    return board.setup(height, width, specifications);
  }

  @Override
  public abstract List<Coord> takeShots();

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> copyOfOpponentShots = new ArrayList<>(List.copyOf(opponentShotsOnBoard));
    List<Coord> shipCoords = board.getShipCoords();
    copyOfOpponentShots.retainAll(shipCoords);
    board.updateShips(copyOfOpponentShots);
    return copyOfOpponentShots;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      board.setCoord(c.getCoordX(), c.getCoordY(), CoordState.HIT);
    }
  }

  @Override
  public void endGame(GameResult result, String reason) {
    return;
  }
}
