package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JoinArgument for Json
 *
 * @param name     is the name of the user
 * @param gameType is the game-type
 */
public record JoinArgument(@JsonProperty("name") String name,
                           @JsonProperty("game-type") String gameType) {
}
