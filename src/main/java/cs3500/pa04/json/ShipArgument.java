package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.CoordArgument;

/**
 * ShipArgumnet for Json
 *
 * @param coord     is a CoordArgument that represents its location
 * @param length    is the length of the ship
 * @param direction is the direction that the ship is facing
 */
public record ShipArgument(@JsonProperty("coord") CoordArgument coord,
                           @JsonProperty("length") int length,
                           @JsonProperty("direction") String direction) {
}
