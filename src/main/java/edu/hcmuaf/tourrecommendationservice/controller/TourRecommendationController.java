package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.dto.LocationResponse;
import edu.hcmuaf.tourrecommendationservice.service.RecommendateService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private RecommendateService recommendateService;

    @GetMapping("/api/recommend/get-recommendations")
    public ResponseEntity<List<LocationResponse>> getRecommendations(@RequestParam long userId, @RequestParam(required = false) Integer numberOfRecommendation, @RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude) throws TasteException, SQLException, IOException {
        List<LocationResponse> recommendation;
        if (numberOfRecommendation == null) {
            numberOfRecommendation = 5;
        }
        if (latitude != null && longitude != null) {
            recommendation = recommendateService.recommend(userId, numberOfRecommendation, latitude, longitude);
        } else {
            recommendation = recommendateService.recommend(userId, numberOfRecommendation);
        }
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }

    @GetMapping("/api/recommend/get-similar-locations")
    public ResponseEntity<List<LocationResponse>> getSimilarLocations(@RequestParam long locationId) throws TasteException, SQLException {
        List<LocationResponse> recommendation;
        recommendation = recommendateService.getMostSimilarItems(locationId);
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }

}
