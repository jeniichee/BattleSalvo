package cs3500.pa04.model.player;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.view.ViewInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the ManualPlayer of a BattleSalvo game
 */
public class ManualPlayer extends AbstractPlayer {
  private final ViewInput view;


  /**
   * Constructor for ManualPlayer
   *
   * @param board is a Board that the ManualPlayer will use and update
   * @param view  is a ViewInput that will be used to input values
   */
  public ManualPlayer(Board board, ViewInput view) {
    super(board);
    this.view = view;
  }

  @Override
  public List<Coord> takeShots() {
    int height = board.getHeight();
    int width = board.getWidth();
    int existingShots = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        CoordState boardVal = board.getCoord(j, i);
        if (boardVal == CoordState.MISSED || boardVal == CoordState.HIT) {
          existingShots += 1;
        }
      }
    }
    ArrayList<Coord> inputShots = new ArrayList<>();
    int shotAmt = Math.min(board.getAliveShips(), height * width - existingShots);
    while (shotAmt > 0) {
      List<Integer> inputValues = view.readInput();
      int x = inputValues.get(0);
      int y = inputValues.get(1);
      Coord tempCoord = new Coord(x, y);
      inputShots.add(tempCoord);
      shotAmt--;
    }
    return inputShots;
  }
}
