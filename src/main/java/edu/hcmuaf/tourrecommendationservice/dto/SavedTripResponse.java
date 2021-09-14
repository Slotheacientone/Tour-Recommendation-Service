package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTrip;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class SavedTripResponse {
    private long userId;
    private long savedTripId;
    private List<Location> savedTripLocations;

    public SavedTripResponse(SavedTrip savedTrip){
        this.userId = savedTrip.getUser().getUserId();
        this.savedTripId = savedTrip.getSavedTripId();
        this.savedTripLocations = savedTrip.getSavedTripLocations();
    }
}
