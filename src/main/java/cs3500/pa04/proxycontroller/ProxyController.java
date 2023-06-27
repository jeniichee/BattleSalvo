package cs3500.pa04.proxycontroller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.Controller;
import cs3500.pa04.json.*;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a controller for handling communication between the server and the AiPlayer.
 * Responsible for sending and receiving JSON messages over a socket connection.
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player aiPlayer;
  private final ObjectMapper mapper;

  /**
   * Constructor for ProxyController
   *
   * @param server is a Socket that represents a server
   * @param player is the AiPlayer that is playing the game
   * @throws IOException when the parameters are invalid
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.mapper = new ObjectMapper();
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.aiPlayer = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      closeConnection();
    }
  }

  /**
   * Determines the type of request the server has sent and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  void delegateMessage(MessageJson message) {
    switch (message.messageName()) {
      case "join" -> handleJoinRequest();
      case "setup" -> handleSetupRequest(message.arguments());
      case "take-shots" -> handleTakeShotsRequest();
      case "report-damage" -> handleRptDmgRequest(message.arguments());
      case "successful-hits" -> handleSuccessfulHits(message.arguments());
      case "end-game" -> handleEndGame(message.arguments());
      default -> throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Process join message sent by server and sends client response to server
   */
  void handleJoinRequest() {
    JoinArgument joinArgs = new JoinArgument("fengma7", "SINGLE");
    JsonNode joinNodeArgs = JsonUtils.serializeRecord(joinArgs);
    MessageJson joinMessage = new MessageJson("join", joinNodeArgs);
    JsonNode joinJson = JsonUtils.serializeRecord(joinMessage);
    this.out.println(joinJson);
  }

  /**
   * Process set-up message sent by server and sends client response to server
   *
   * @param arguments setup message sent by server to client
   */
  void handleSetupRequest(JsonNode arguments) {
    int width = arguments.get("width").asInt();
    int height = arguments.get("height").asInt();

    JsonNode fleetSpec = arguments.get("fleet-spec");
    Map<ShipType, Integer> shipMap = new LinkedHashMap<>();
    shipMap.put(ShipType.CARRIER, fleetSpec.get("CARRIER").asInt());
    shipMap.put(ShipType.BATTLESHIP, fleetSpec.get("BATTLESHIP").asInt());
    shipMap.put(ShipType.DESTROYER, fleetSpec.get("DESTROYER").asInt());
    shipMap.put(ShipType.SUBMARINE, fleetSpec.get("SUBMARINE").asInt());

    List<Ship> manualShips = aiPlayer.setup(height, width, shipMap);
    ArrayList<ShipArgument> shipArgs = new ArrayList<>();
    for (Ship s : manualShips) {
      int x = s.getPosition().get(0).getCoordX();
      int y = s.getPosition().get(0).getCoordY();
      int size = s.getType().getSize();
      String direction = s.getShipDirection().getDirection();
      ShipArgument tempShipArg = new ShipArgument(new CoordArgument(x, y), size, direction);
      shipArgs.add(tempShipArg);
    }
    ShipArgument[] shipArgsArr = new ShipArgument[shipArgs.size()];
    shipArgsArr = shipArgs.toArray(shipArgsArr);
    FleetArgument fleetArgs = new FleetArgument(shipArgsArr);
    JsonNode fleetNodeArgs = JsonUtils.serializeRecord(fleetArgs);
    MessageJson setupFleet = new MessageJson("setup", fleetNodeArgs);
    JsonNode setup = JsonUtils.serializeRecord(setupFleet);
    this.out.println(setup);
  }

  /**
   * Process take-shots message sent by server and sends client response to server
   */
  void handleTakeShotsRequest() {
    List<Coord> aiShots = aiPlayer.takeShots();
    System.out.println();
    ArrayList<CoordArgument> coordArgs = new ArrayList<>();
    for (Coord c : aiShots) {
      CoordArgument tempCoordArgs = new CoordArgument(c.getCoordX(), c.getCoordY());
      coordArgs.add(tempCoordArgs);
    }
    CoordArgument[] coordArgsArr = new CoordArgument[coordArgs.size()];
    coordArgsArr = coordArgs.toArray(coordArgsArr);
    VolleyArgument volleyArgs = new VolleyArgument(coordArgsArr);
    JsonNode volley = JsonUtils.serializeRecord(volleyArgs);
    MessageJson takeShots = new MessageJson("take-shots", volley);
    JsonNode shots = JsonUtils.serializeRecord(takeShots);
    this.out.println(shots);
  }

  /**
   * Process report-damage message sent by server and sends client response to server
   *
   * @param arguments report-damage message sent by server to client
   */
  void handleRptDmgRequest(JsonNode arguments) {
    ArrayList<Coord> serverShots = new ArrayList<>();
    VolleyArgument volleyArgs = JsonUtils.receiveVolley(arguments);
    CoordArgument[] coords = volleyArgs.coords();
    for (CoordArgument c : coords) {
      serverShots.add(new Coord(c.x(), c.y()));
    }
    List<Coord> serverHits = aiPlayer.reportDamage(serverShots);
    ArrayList<CoordArgument> coordHitArgs = new ArrayList<>();

    for (Coord c : serverHits) {
      CoordArgument tempCoordArgs = new CoordArgument(c.getCoordX(), c.getCoordY());
      coordHitArgs.add(tempCoordArgs);
    }

    CoordArgument[] hitArgs = new CoordArgument[coordHitArgs.size()];
    hitArgs = coordHitArgs.toArray(hitArgs);
    VolleyArgument volleyHitArgs = new VolleyArgument(hitArgs);
    JsonNode volleyHit = JsonUtils.serializeRecord(volleyHitArgs);
    MessageJson damage = new MessageJson("report-damage", volleyHit);
    JsonNode reportDamage = JsonUtils.serializeRecord(damage);
    this.out.println(reportDamage);
  }

  /**
   * Process successful-hits message sent by server
   *
   * @param arguments Successful-hits message sent by server to client
   */
  void handleSuccessfulHits(JsonNode arguments) {
    ArrayList<Coord> successShots = new ArrayList<>();
    VolleyArgument volleyArgs = JsonUtils.receiveVolley(arguments);
    CoordArgument[] coords = volleyArgs.coords();
    for (CoordArgument c : coords) {
      successShots.add(new Coord(c.x(), c.y()));
    }
    aiPlayer.successfulHits(successShots);
    MessageJson success = new MessageJson("successful-hits", mapper.createObjectNode());
    JsonNode successfulHits = JsonUtils.serializeRecord(success);
    this.out.println(successfulHits);
  }

  /**
   * Process end-game message sent by server
   *
   * @param arguments end-game message sent by server to client
   */
  void handleEndGame(JsonNode arguments) {
    String result = arguments.get("result").asText();
    String reason = arguments.get("reason").asText();
    MessageJson endGame = new MessageJson("end-game", mapper.createObjectNode());
    JsonNode endGameNode = JsonUtils.serializeRecord(endGame);
    this.out.println(endGameNode);

  }

  /**
   * closes connection between client and server
   */
  void closeConnection() {
    try {
      in.close();
      out.close();
      server.close();
    } catch (IOException e) {
      System.err.println("Error occurred while closing the connection: " + e.getMessage());
    }
  }

}
