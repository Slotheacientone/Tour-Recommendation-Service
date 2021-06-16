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
public class RecommendEntity {

    /** User id. */
    private long userId;

    /** Location id. */
    private long locationId;

    /** User rating. */
    private float rating;

    /** Location name. */
    private String locationName;

    /** Location latitude. */
    private double locationLatitude;

    /** Location longtitude. */
    private double locationLongtitude;


}
