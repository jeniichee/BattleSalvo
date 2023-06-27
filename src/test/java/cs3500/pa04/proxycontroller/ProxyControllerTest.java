package cs3500.pa04.proxycontroller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.json.CoordArgument;
import cs3500.pa04.json.FleetArgument;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ShipArgument;
import cs3500.pa04.json.VolleyArgument;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private boolean messageDelegated;
  private MessageJson delegatedMessage;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Tests an InvalidMessageName
   *
   * @throws IOException if the Socket parameter is invalid
   */
  @Test
  public void testInvalidMessageName() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ObjectMapper mapper = new ObjectMapper();
    MockPlayer mocus = new MockPlayer();
    ProxyController proxy = new ProxyController(mocket, mocus);

    MessageJson invalidMessage = new MessageJson("invalid-message", mapper.createObjectNode());

    assertThrows(IllegalStateException.class, () -> proxy.delegateMessage(invalidMessage));
  }

  /**
   * Tests DelegateJoin
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateJoin() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ObjectMapper mapper = new ObjectMapper();
    MockPlayer mocus = new MockPlayer();

    MessageJson joinMessage = new MessageJson("join", mapper.createObjectNode());

    ProxyController proxy = new ProxyController(mocket, mocus);
    assertDoesNotThrow(() -> proxy.delegateMessage(joinMessage));
  }

  /**
   * Tests DelegateSetup
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateSetup() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();

    JsonNode arguments = createSetupArguments(10, 10, 1, 2,
        3, 4);
    MessageJson messageJson = new MessageJson("setup", arguments);
    JsonNode setupJson = JsonUtils.serializeRecord(messageJson);

    Mocket mocket = new Mocket(testLog, List.of(setupJson.toString()));
    ProxyController proxy = new ProxyController(mocket, new MockPlayer());
    proxy.handleSetupRequest(messageJson.arguments());

    assertDoesNotThrow(() -> proxy.delegateMessage(messageJson));

  }

  /**
   * Tests DelegateTakeShots
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateTakeShots() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ObjectMapper mapper = new ObjectMapper();
    MockPlayer mocus = new MockPlayer();

    MessageJson takeShotsMessage = new MessageJson("take-shots", mapper.createObjectNode());

    ProxyController proxy = new ProxyController(mocket, mocus);
    assertDoesNotThrow(() -> proxy.delegateMessage(takeShotsMessage));
  }

  /**
   * Tests DelegateReportDamage
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateReportDamage() throws IOException {
    List<Coord> damageCoords = new ArrayList<>();
    damageCoords.add(new Coord(1, 2));
    damageCoords.add(new Coord(3, 4));

    ArrayList<CoordArgument> coordArgs = new ArrayList<>();
    for (Coord c : damageCoords) {
      CoordArgument tempCoordArgs = new CoordArgument(c.getCoordX(), c.getCoordY());
      coordArgs.add(tempCoordArgs);
    }
    CoordArgument[] coordArgsArr = coordArgs.toArray(new CoordArgument[0]);
    VolleyArgument volleyArgs = new VolleyArgument(coordArgsArr);
    JsonNode volley = JsonUtils.serializeRecord(volleyArgs);
    MessageJson reportDamageMessage = new MessageJson("report-damage", volley);

    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    MockPlayer mocus = new MockPlayer();

    ProxyController proxy = new ProxyController(mocket, mocus);
    assertDoesNotThrow(() -> proxy.delegateMessage(reportDamageMessage));
  }

  /**
   * Tests DelegateSuccessfulHits
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateSuccessfulHits() throws IOException {
    List<Coord> hitCoords = new ArrayList<>();
    hitCoords.add(new Coord(1, 2));
    hitCoords.add(new Coord(3, 4));
    ArrayList<CoordArgument> coordArgs = new ArrayList<>();
    for (Coord c : hitCoords) {
      CoordArgument tempCoordArgs = new CoordArgument(c.getCoordX(), c.getCoordY());
      coordArgs.add(tempCoordArgs);
    }
    CoordArgument[] coordArgsArr = coordArgs.toArray(new CoordArgument[0]);
    VolleyArgument volleyArgs = new VolleyArgument(coordArgsArr);
    JsonNode volley = JsonUtils.serializeRecord(volleyArgs);
    MessageJson successfulHitsMessage = new MessageJson("successful-hits", volley);

    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    MockPlayer mocus = new MockPlayer();
    ProxyController proxy = new ProxyController(mocket, mocus);
    assertDoesNotThrow(() -> proxy.delegateMessage(successfulHitsMessage));
  }

  /**
   * Tests DelegateEndGame
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testDelegateEndGame() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ObjectMapper mapper = new ObjectMapper();
    MockPlayer mocus = new MockPlayer();

    // Prepare end-game message
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", "win");
    arguments.put("reason", "You sank all enemy ships!");
    MessageJson endGameMessage = new MessageJson("end-game", arguments);

    ProxyController proxy = new ProxyController(mocket, mocus);
    assertDoesNotThrow(() -> proxy.delegateMessage(endGameMessage));
  }

  /**
   * Tests HandleJoinRequest
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleJoinRequest() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);

    ProxyController proxyController = new ProxyController(mocket, new MockPlayer());
    proxyController.handleJoinRequest();

    String expected = "{\"method-name\":\"join\",\"arguments\":"
        + "{\"name\":\"fengma7\",\"game-type\":\"SINGLE\"}}"
        + System.lineSeparator();

    assertEquals(expected, testLog.toString());
  }


  /**
   * Tests HandleSetupRequestServer
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleSetupRequestServer() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();

    JsonNode arguments = createSetupArguments(10, 10, 1, 2,
        3, 4);
    MessageJson messageJson = new MessageJson("setup", arguments);
    JsonNode setupJson = JsonUtils.serializeRecord(messageJson);

    Mocket mocket = new Mocket(testLog, List.of(setupJson.toString()));
    ProxyController proxy = new ProxyController(mocket, new MockPlayer());
    proxy.handleSetupRequest(messageJson.arguments());

    String expected = "{\"method-name\":\"setup\",\"arguments\""
        + ":{\"width\":10,\"height\":10,\"fleet-spec\":"
        + "{\"CARRIER\":1,\"BATTLESHIP\":2,\"DESTROYER\":3,\"SUBMARINE\":4}}}";

    assertEquals(expected, setupJson.toString());
  }


  /**
   * Tests HandleSetupRequestClientResponse
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleSetupRequestClientResponse() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    MockPlayer mocus = new MockPlayer();

    int width = 10;
    int height = 10;
    Map<ShipType, Integer> shipMap = new LinkedHashMap<>();
    shipMap.put(ShipType.CARRIER, 1);
    shipMap.put(ShipType.BATTLESHIP, 2);
    shipMap.put(ShipType.DESTROYER, 3);
    shipMap.put(ShipType.SUBMARINE, 4);
    List<Ship> ships = mocus.setup(height, width, shipMap);

    ArrayList<ShipArgument> shipArgs = new ArrayList<>();
    for (Ship s : ships) {
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

    JsonNode arguments = createSetupArguments(10, 10, 1, 2,
        3, 4);
    MessageJson messageJson = new MessageJson("setup", arguments);
    JsonNode setupJson = JsonUtils.serializeRecord(messageJson);

    Mocket mocket = new Mocket(testLog, List.of(setupJson.toString()));
    ProxyController proxy = new ProxyController(mocket, mocus);
    proxy.handleSetupRequest(messageJson.arguments());

    assertEquals(setup.toString() + System.lineSeparator(), testLog.toString());
  }


  /**
   * Tests TakeShotsRequestClient
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleTakeShotsRequestClient() throws IOException {
    MockPlayer player = new MockPlayer();
    player.takeShots();

    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ProxyController proxy = new ProxyController(mocket, player);
    proxy.handleTakeShotsRequest();

    String expected = "{\"method-name\":\"take-shots\",\"arguments\""
        + ":{\"coordinates\":[{\"x\":1,\"y\":2},"
        + "{\"x\":0,\"y\":2},{\"x\":5,\"y\":4},{\"x\":6,\"y\":3}]}}" + System.lineSeparator();

    assertEquals(expected, testLog.toString());
  }

  /**
   * Tests HandleRptDmgRequest
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleRptDmgRequest() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    MockPlayer mocus = new MockPlayer();

    ArrayList<Coord> serverShots = new ArrayList<>();
    serverShots.add(new Coord(0, 0));
    serverShots.add(new Coord(1, 1));
    serverShots.add(new Coord(2, 2));
    serverShots.add(new Coord(3, 3));

    List<Coord> serverHits = mocus.reportDamage(serverShots);

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

    Mocket mocket = new Mocket(testLog, List.of(reportDamage.toString()));
    ProxyController proxy = new ProxyController(mocket, mocus);
    proxy.handleRptDmgRequest(damage.arguments());

    String expected = reportDamage.toString() + System.lineSeparator();
    String serverLogs = testLog.toString();
    assertEquals(expected, serverLogs);
  }

  /**
   * Tests HandleSuccessfulHits
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  void testHandleSuccessfulHits() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    MockPlayer mocus = new MockPlayer();

    ArrayList<Coord> successShots = new ArrayList<>();
    successShots.add(new Coord(1, 1));
    successShots.add(new Coord(3, 3));

    mocus.successfulHits(successShots);

    CoordArgument arg1 = new CoordArgument(1, 1);
    CoordArgument arg2 = new CoordArgument(3, 3);
    CoordArgument[] arg = {arg1, arg2};

    VolleyArgument volleyArg = new VolleyArgument(arg);

    ObjectMapper mapper = new ObjectMapper();
    String volleyJson = mapper.writeValueAsString(volleyArg);
    JsonNode successfulHits = mapper.readTree(volleyJson);

    Mocket mocket = new Mocket(testLog, List.of(successfulHits.toString()));
    ProxyController proxy = new ProxyController(mocket, mocus);
    proxy.handleSuccessfulHits(successfulHits);

    List<Coord> actualCoords = new ArrayList<>();
    JsonNode coordinatesNode = successfulHits.get("coordinates");
    if (coordinatesNode.isArray()) {
      for (JsonNode jsonNode : coordinatesNode) {
        int x = jsonNode.get("x").asInt();
        int y = jsonNode.get("y").asInt();
        Coord coord = new Coord(x, y);
        actualCoords.add(coord);
      }
    }

    assertEquals(successShots, actualCoords);
  }

  /**
   * Tests HandleEndGame
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  public void testHandleEndGame() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> toSend = new ArrayList<>();
    Mocket mocket = new Mocket(testLog, toSend);
    ObjectMapper mapper = new ObjectMapper();
    MockPlayer mocus = new MockPlayer();

    String result = "win";
    String reason = "Game over!";

    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", result);
    arguments.put("reason", reason);

    ProxyController proxy = new ProxyController(mocket, mocus);
    proxy.handleEndGame(arguments);

    MessageJson expectedEndGame = new MessageJson("end-game", mapper.createObjectNode());
    JsonNode expectedEndGameNode = JsonUtils.serializeRecord(expectedEndGame);

    String serverLogs = testLog.toString();
    assertEquals(expectedEndGameNode.toString() + System.lineSeparator(), serverLogs);
  }


  /**
   * Tests run
   *
   * @throws IOException if Socket parameter is invalid
   */
  @Test
  void run() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    MessageJson join = new MessageJson("join", mapper.createObjectNode());
    JsonNode joinJson = JsonUtils.serializeRecord(join);

    MessageJson set = new MessageJson("setup", mapper.createObjectNode());
    JsonNode setupJson = JsonUtils.serializeRecord(set);

    MessageJson end = new MessageJson("end-game", mapper.createObjectNode());
    JsonNode endJson = JsonUtils.serializeRecord(end);

    ArrayList<JsonNode> msg = new ArrayList<>();
    msg.add(joinJson);
    msg.add(setupJson);
    msg.add(endJson);

    List<String> toSend = new ArrayList<>();
    toSend.add(msg.toString());

    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    Mocket mocket = new Mocket(testLog, toSend);

    MockPlayer mocus = new MockPlayer();
    ProxyController proxy = new ProxyController(mocket, mocus);

    String expected =
        "[[{\"method-name\":\"join\",\"arguments\":{}}, "
            + "{\"method-name\":\"setup\",\"arguments\":{}}, "
            + "{\"method-name\":\"end-game\",\"arguments\":{}}]]";

    proxy.run();
    String logOutput = toSend.toString();
    assertEquals(expected, logOutput);
  }


  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  private static JsonNode createSetupArguments(int height, int width, int carrierCount,
                                               int battleshipCount, int destroyerCount,
                                               int submarineCount) {

    Map<String, Integer> fleetSpec = new LinkedHashMap<>();
    fleetSpec.put("CARRIER", carrierCount);
    fleetSpec.put("BATTLESHIP", battleshipCount);
    fleetSpec.put("DESTROYER", destroyerCount);
    fleetSpec.put("SUBMARINE", submarineCount);

    Map<String, Object> arguments = new LinkedHashMap<>();
    arguments.put("width", width);
    arguments.put("height", height);
    arguments.put("fleet-spec", fleetSpec);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.valueToTree(arguments);
  }

}
