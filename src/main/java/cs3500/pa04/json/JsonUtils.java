package cs3500.pa04.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simple utils class used to hold static methods that help with serializing and deserializing JSON.
 */
public class JsonUtils {
  private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts a given record object to a JsonNode.
     *
     * @param record the record to convert
     * @return the JsonNode representation of the given record
     * @throws IllegalArgumentException if the record could not be converted correctly
     */
    public static JsonNode serializeRecord(Record record) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(record, JsonNode.class);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Given record cannot be serialized");
      }
    }

  /**
   * Receives a JsonNode and converts it to a VolleyArgument
   *
   * @param record is a JsonNode that will be converted to a record
   * @return a record in the format of the VolleyArgument
   * @throws IllegalArgumentException if the JsonNode is invalid
   */
  public static VolleyArgument receiveVolley(JsonNode record) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.convertValue(record, VolleyArgument.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given JsonNode cannot be serialized");
    }
  }
}