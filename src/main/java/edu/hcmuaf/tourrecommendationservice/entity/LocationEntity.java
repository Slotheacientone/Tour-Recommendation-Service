package edu.hcmuaf.tourrecommendationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Recommendation entity.
 *
 * @author Viet-PH
 */
@Data
@Setter
@Getter
public class LocationEntity {

    /** location id. */
    @JsonProperty("id")
    private long locationId;

    /** location name. */
    @JsonProperty("name")
    private String locationName;

    /** location latitude. */
    @JsonProperty("latitude")
    private double locationLatitude;

    /** location longtitude. */
    @JsonProperty("longtitude")
    private double locationLongtitude;
}
