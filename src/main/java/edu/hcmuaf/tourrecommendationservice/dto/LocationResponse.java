package edu.hcmuaf.tourrecommendationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LocationResponse {
    protected long locationId;
    protected String locationName;
    protected String locationImageUrl;
    protected float ratings;
    protected int numberOfPeopleRating;
    /**
     * Location latitude.
     */
    protected double locationLatitude;

    /**
     * Location longtitude.
     */
    protected double locationLongitude;

    protected long distance;
}
