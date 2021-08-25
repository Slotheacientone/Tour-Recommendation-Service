package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LocationEntity {
    /** Location id. */
    private long locationId;
    private String locationName;
    private String locationImageUrl;
    private float ratings;
    private float recommendScore;
    private int numberOfPeopleRating;
    /**
     * Location latitude.
     */
    private double locationLatitude;

    /**
     * Location longtitude.
     */
    private double locationLongitude;

    private long distance;
    private int wishListOrder;
}
