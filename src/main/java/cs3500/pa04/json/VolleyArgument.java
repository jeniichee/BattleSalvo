package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.CoordArgument;

/**
 * VolleyArgument for Json
 *
 * @param coords is a list of CoordArguments
 */
public record VolleyArgument(
    @JsonProperty("coordinates") CoordArgument[] coords) {

}
