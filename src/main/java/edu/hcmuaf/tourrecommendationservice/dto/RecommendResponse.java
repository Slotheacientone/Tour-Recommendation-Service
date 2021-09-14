package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import lombok.Data;

@Data
public class RecommendResponse extends LocationResponse {

    private float recommendScore;

    public RecommendResponse(Location location, float recommendScore){
        this.locationId = location.getLocationId();
        this.locationName = location.getLocationName();
        this.locationImageUrl = location.getLocationImageUrl();
        this.ratings = location.getRatings();
        this.numberOfPeopleRating = location.getNumberOfPeopleRating();
        this.locationLatitude = location.getLocationLatitude();
        this.locationLongitude = location.getLocationLongitude();
        this.recommendScore = recommendScore;
    }
}
