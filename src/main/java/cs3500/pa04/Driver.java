package cs3500.pa04;

import cs3500.pa04.model.board.Board;
import cs3500.pa04.model.board.BoardImpl;
import cs3500.pa04.model.player.AiPlayer;
import cs3500.pa04.proxycontroller.ProxyController;

import java.io.IOException;
import java.net.Socket;


/**
 * Driver/main class
 */
public class Driver {

  /**
   * Main method used to run the BattleSalvo game
   *
   * @param args is the arguments of the main
   */
  public static void main(String[] args) {

    try {
      Socket socket = new Socket("0.0.0.0", 35001);

      Board aiBoard = new BoardImpl();

      AiPlayer aiPlayer = new AiPlayer(aiBoard);

      ProxyController proxyControl = new ProxyController(socket, aiPlayer);
      proxyControl.run();

    } catch (IOException e) {
      System.out.println(":(((");
    }
  }
}
