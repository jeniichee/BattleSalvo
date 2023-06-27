package cs3500.pa04.proxycontroller;

import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.player.GameResult;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.Direction;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.view.ViewInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A mock implementation of the Player interface for testing purposes.
 */
public class MockPlayer implements Player {
  public List<Ship> setupShips;
  public List<Coord> takeShotsResult;
  public List<Coord> reportDamageResult;
  public List<Coord> successfulHitsInput;
  private String name;
  private BoardImpl board;
  private ViewInput view;

  /**
   * Constructor for MockPlayer.
   * Initializes the lists and creates a new empty board.
   */
  public MockPlayer() {
    this.setupShips = new ArrayList<>();
    this.takeShotsResult = new ArrayList<>();
    this.reportDamageResult = new ArrayList<>();
    this.successfulHitsInput = new ArrayList<>();
    this.board = new BoardImpl();
  }

  /**
   * Constructs a new instance of {@code MockPlayer} with the specified board and view.
   *
   * @param board the game board
   * @param view  the view for the player
   */
  public MockPlayer(BoardImpl board, ViewInput view) {
    this.board = board;
    this.view = view;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> shipMap) {
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

    setupShips = mockShips;

    return setupShips;
  }

  @Override
  public List<Coord> takeShots() {
    Coord coord = new Coord(1, 2);
    Coord coord1 = new Coord(0, 2);
    Coord coord2 = new Coord(5, 4);
    Coord coord3 = new Coord(6, 3);

    List<Coord> sheet = new ArrayList<>();
    sheet.add(coord);
    sheet.add(coord1);
    sheet.add(coord2);
    sheet.add(coord3);

    takeShotsResult = sheet;
    return takeShotsResult;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> shots) {
    return reportDamageResult;
  }

  @Override
  public void successfulHits(List<Coord> shots) {
    successfulHitsInput.addAll(shots);
  }


  @Override
  public void endGame(GameResult result, String reason) {

  }

}
