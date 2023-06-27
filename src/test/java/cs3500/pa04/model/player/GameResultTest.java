package cs3500.pa04.model.player;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests GameResult enum
 */
class GameResultTest {

  /**
   * Tests values method
   */
  @Test
  void testValues() {
    GameResult[] values = GameResult.values();

    GameResult[] expectedValues = {
        GameResult.WIN,
        GameResult.LOSE,
        GameResult.DRAW
    };

    assertArrayEquals(values, expectedValues);
  }

  /**
   * Tests valueOf method
   */
  @Test
  void testValueOf() {
    GameResult win = GameResult.valueOf("WIN");
    GameResult lose = GameResult.valueOf("LOSE");
    GameResult draw = GameResult.valueOf("DRAW");
    assertEquals(GameResult.WIN, win);
    assertEquals(GameResult.LOSE, lose);
    assertEquals(GameResult.DRAW, draw);
  }
}