package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import lombok.Data;

import java.util.List;

@Data
public class SavedTripRequest {
    private long userId;
    private long savedTripId;
    private List<Location> savedTripLocations;
}
