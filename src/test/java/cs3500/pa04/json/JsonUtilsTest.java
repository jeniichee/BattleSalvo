package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for JsonUtils
 */
class JsonUtilsTest {
  private JsonUtils testUtils;

  /**
   * Represents a Null Record
   *
   * @param name is a person's name
   * @param date is a date
   */
  public record NullRecord(String name, LocalDate date) {
  }

  /**
   * Setup for tests
   */
  @BeforeEach
  public void setup() {
    testUtils = new JsonUtils();
  }

  /**
   * Tests for an InvalidSerializeRecord
   */
  @Test
  public void testInvalidSerializeRecord() {
    NullRecord invalidRecord = new NullRecord("exampleMethod", LocalDate.now());
    assertThrows(IllegalArgumentException.class, () -> testUtils.serializeRecord(invalidRecord));
  }

  /**
   * Test for an InvalidReceiveVolley
   */
  @Test
  public void testInvalidReceiveVolley() {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode invalidJsonNode = mapper.createObjectNode()
        .put("name", "John Doe")
        .put("age", 30)
        .put("address", "123 Main Street");
    assertThrows(IllegalArgumentException.class, () -> testUtils.receiveVolley(invalidJsonNode));
  }
}
