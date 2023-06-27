package cs3500.pa04.controller;

import cs3500.pa04.model.board.BoardState;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordState;
import cs3500.pa04.model.player.GameResult;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.view.ViewOutput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LocalController is the implementation of Controller
 */
public class LocalController implements Controller {
  private final Player p1;
  private final Player p2;
  private final BoardState b1;
  private final BoardState b2;
  private final ViewOutput view;
  private Readable in;
  private Appendable out;


  /**
   * Constructor for LocalController
   *
   * @param p1   is the first Player, which is a ManualPlayer for this program
   * @param p2   is the second Player, which is an AiPlayer for this program
   * @param b1   is the BoardState for the first Player
   * @param b2   is the BoardState for the second Player
   * @param view is the view class
   */
  public LocalController(Player p1, Player p2, BoardState b1, BoardState b2,
                         ViewOutput view) {
    this.p1 = p1;
    this.p2 = p2;
    this.b1 = b1;
    this.b2 = b2;
    this.view = view;
  }

  /**
   * SizeSelection is a helper method used to obtain the user input for the board dimensions
   * and storing it into a Coord
   *
   * @return a Coord that has the width and height dimensions of the board
   */
  public Coord sizeSelection() {
    view.printInputDimension();
    List<Integer> inputValues = view.readInput();
    int y = inputValues.get(0);
    int x = inputValues.get(1);
    if (x >= 6 && x <= 15 && y >= 6 && y <= 15) {
      return new Coord(x, y);
    } else {
      view.printInvalidInputDimension();
      return sizeSelection();
    }
  }

  /**
   * ShipSelectionSetup is a helper method that is used to take the user input in for the amount
   * of each ship (Carrier, BattleShip, Destroyer, and Submarine) for the BattleSalvoGame and
   * creating a map with those values
   *
   * @param x is an Integer that represents the width of the board
   * @param y is an Integer that represents the height of the board
   * @return a Map that holds the ship type to each Integer
   */
  public Map<ShipType, Integer> shipSelectionSetup(int x, int y) {
    Map<ShipType, Integer> shipSelect = new LinkedHashMap<>();
    view.printFleetSelection(Math.min(x, y));
    boolean validShipChoice = false;
    int carrierSize = 0;
    int battleSize = 0;
    int destroyerSize = 0;
    int submarineSize = 0;

    while (!validShipChoice) {
      List<Integer> inputValues = view.readInput();
      carrierSize = inputValues.get(0);
      battleSize = inputValues.get(1);
      destroyerSize = inputValues.get(2);
      submarineSize = inputValues.get(3);
      int sum = carrierSize + battleSize + destroyerSize + submarineSize;
      if (carrierSize > 0 && battleSize > 0 && destroyerSize > 0 && submarineSize > 0
          && sum <= Math.min(x, y)) {
        validShipChoice = true;
      } else {
        view.printInvalidFleet(Math.min(x, y));
      }
    }

    shipSelect.put(ShipType.CARRIER, carrierSize);
    shipSelect.put(ShipType.BATTLESHIP, battleSize);
    shipSelect.put(ShipType.DESTROYER, destroyerSize);
    shipSelect.put(ShipType.SUBMARINE, submarineSize);
    return shipSelect;

  }

  /**
   * ManualShots is a method that is used to obtain a user's shots using the Player's takeShot
   * method
   *
   * @param height      is an Integer representing the height of the board
   * @param width       is an Integer representing the width of the board
   * @param manualShips is the List of ships relating
   * @return is a List of Coords that represents the manual Player's shots
   */
  public List<Coord> manualShots(int height, int width, List<Ship> manualShips,
                                 List<Coord> previousShots) {
    int existingShotsAmt = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        CoordState boardVal = b1.getCoord(j, i);
        if (boardVal == CoordState.MISSED || boardVal == CoordState.HIT) {
          existingShotsAmt += 1;
        }
      }
    }
    int aliveShipCount = 0;
    for (Ship s : manualShips) {
      if (s.isAlive()) {
        aliveShipCount++;
      }
    }
    boolean invalidShots;
    List<Coord> manualShots;
    do {
      invalidShots = false;
      view.printInputShot(Math.min(aliveShipCount, height * width - existingShotsAmt));
      manualShots = p1.takeShots();
      for (Coord c : manualShots) {
        if (c.getCoordX() >= width || c.getCoordX() < 0 || c.getCoordY() >= height
            || c.getCoordY() < 0 || previousShots.contains(c)) {
          invalidShots = true;
          break;
        }
      }
    } while (invalidShots);
    return manualShots;
  }

  /**
   * GetAllShipsCoords is a helper method that is used to get all the position coords from a list
   * of ships
   *
   * @param ships is a List of ships
   * @return a List of Coord that represents all the position coords for all the ships
   */
  public List<Coord> getAllShipCoords(List<Ship> ships) {
    ArrayList<Coord> shipCoords = new ArrayList<>();
    for (Ship s : ships) {
      shipCoords.addAll(s.getPosition());
    }
    return shipCoords;
  }

  /**
   * SetUserMissedCoords is a method used to add the missed coords to the user's board
   *
   * @param allShots is a List of Coord that represents all the user's shots
   * @param hitShots is a List of Coord that represents all the user's hit shots
   */
  public void setUserMissedCoords(List<Coord> allShots, List<Coord> hitShots) {
    ArrayList<Coord> tempAllShots = new ArrayList<>(List.copyOf(allShots));
    tempAllShots.removeAll(hitShots);
    for (Coord c : tempAllShots) {
      b1.setCoord(c.getCoordX(), c.getCoordY(), CoordState.MISSED);
    }
  }

  /**
   * SetAiMissedCoord is a method used to add the missed coords to the Ai's board
   *
   * @param allShots is a List of Coord that represents all the Ai's shots
   * @param hitShots is a List of Coord that represents all the Ai's hit shots
   */
  public void setAiMissedCoord(List<Coord> allShots, List<Coord> hitShots) {
    ArrayList<Coord> tempAllShots = new ArrayList<>(List.copyOf(allShots));
    tempAllShots.removeAll(hitShots);
    for (Coord c : tempAllShots) {
      b2.setCoord(c.getCoordX(), c.getCoordY(), CoordState.MISSED);
    }
  }

  /**
   * Is a function used to run the BattleSalvo game
   */
  @Override
  public void run() {
    view.printWelcome();
    Coord dim = sizeSelection();
    int height = dim.getCoordY();
    int width = dim.getCoordX();
    Map<ShipType, Integer> shipMap = shipSelectionSetup(width, height);
    List<Ship> manualShips = p1.setup(height, width, shipMap);
    List<Ship> aiShips = p2.setup(height, width, shipMap);
    ArrayList<Coord> totalAiShots = new ArrayList<>();
    ArrayList<Coord> totalManualShots = new ArrayList<>();
    ArrayList<Coord> totalDamageOnManual = new ArrayList<>();
    ArrayList<Coord> totalDamageOnAi = new ArrayList<>();
    while (!b1.isGameOver() && !b2.isGameOver()) {
      view.printB1Board(height, width);
      System.out.println();
      view.printManualBoard(height, width, getAllShipCoords(manualShips));

      List<Coord> manualShots = manualShots(height, width, manualShips, totalManualShots);
      List<Coord> aiShots = p2.takeShots();
      List<Coord> manualDamageTaken = p1.reportDamage(aiShots);
      List<Coord> aiDamageTaken = p2.reportDamage(manualShots);
      setUserMissedCoords(manualShots, aiDamageTaken);
      totalAiShots.addAll(aiShots);

      totalDamageOnManual.addAll(manualDamageTaken);
      totalDamageOnAi.addAll(aiDamageTaken);
      setUserMissedCoords(manualShots, aiDamageTaken);
      setAiMissedCoord(aiShots, manualDamageTaken);

      p1.successfulHits(aiDamageTaken);
      p2.successfulHits(manualDamageTaken);
    }
    if (b1.isGameOver() && !b2.isGameOver()) {
      p1.endGame(GameResult.LOSE, "All your ships have been sunk.");
    } else if (!b1.isGameOver() && b2.isGameOver()) {
      p1.endGame(GameResult.WIN, "You sunk all the enemy's ships!");
    } else {
      p1.endGame(GameResult.DRAW, "Both team's ships have all been sunk.");
    }
  }
}
