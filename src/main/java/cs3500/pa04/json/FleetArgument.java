package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FleetArgument for Json
 *
 * @param ships is a list of ship arguments
 */
public record FleetArgument(@JsonProperty("fleet") ShipArgument[] ships) {
}
