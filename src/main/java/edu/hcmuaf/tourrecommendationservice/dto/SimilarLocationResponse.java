package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import lombok.Data;

@Data
public class SimilarLocationResponse extends LocationResponse{

    public SimilarLocationResponse(Location location){
        this.locationId = location.getLocationId();
        this.locationName = location.getLocationName();
        this.locationImageUrl = location.getLocationImageUrl();
        this.ratings = location.getRatings();
        this.numberOfPeopleRating = location.getNumberOfPeopleRating();
        this.locationLatitude = location.getLocationLatitude();
        this.locationLongitude = location.getLocationLongitude();
    }
}
