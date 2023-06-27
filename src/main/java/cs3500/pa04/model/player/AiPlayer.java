package cs3500.pa04.model.player;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.coord.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AiPlayer that will automatically play the BattleSalvo game
 */
public class AiPlayer extends AbstractPlayer {
  protected final List<Coord> previousShots;


  /**
   * Constructor for AiPlayer
   *
   * @param board is the Board that the AiPlayer will use
   */
  public AiPlayer(Board board) {
    super(board);
    previousShots = new ArrayList<>();
  }

  @Override
  public List<Coord> takeShots() {
    int x = board.getWidth();
    int y = board.getHeight();
    Random r = new Random(System.currentTimeMillis());
    ArrayList<Coord> inputShots = new ArrayList<>();
    int aliveShips = board.getAliveShips();
    int shotAmt = Math.min(aliveShips, x * y - previousShots.size());
    while (shotAmt > 0) {
      int shotX = r.nextInt(x);
      int shotY = r.nextInt(y);
      Coord tempCoord = new Coord(shotX, shotY);
      if (!previousShots.contains(tempCoord)) {
        previousShots.add(tempCoord);
        inputShots.add(tempCoord);
        shotAmt--;
      }
    }
    return inputShots;
  }
}
