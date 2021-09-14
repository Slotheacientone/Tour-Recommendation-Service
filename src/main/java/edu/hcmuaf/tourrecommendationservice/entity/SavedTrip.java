package edu.hcmuaf.tourrecommendationservice.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SavedTrip {
    private User user;
    private long savedTripId;
    private List<Location> savedTripLocations;

}
