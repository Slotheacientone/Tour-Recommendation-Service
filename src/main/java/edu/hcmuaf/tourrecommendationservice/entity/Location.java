package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Location {
    /** Location id. */
    private long locationId;
    private String locationName;
    private String locationImageUrl;
    private float ratings;
    private int numberOfPeopleRating;
    private String category;
    /**
     * Location latitude.
     */
    private double locationLatitude;

    /**
     * Location longtitude.
     */
    private double locationLongitude;

}
