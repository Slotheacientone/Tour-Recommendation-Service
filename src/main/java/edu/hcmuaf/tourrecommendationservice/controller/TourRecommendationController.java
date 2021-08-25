package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.service.RecommendateService;
import edu.hcmuaf.tourrecommendationservice.service.SortRecommendService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class TourRecommendationController {

    @Autowired
    private RecommendateService recommendateService;

    @GetMapping("/api/recommend/get-recommendations")
    public ResponseEntity<List<LocationEntity>> getRecommendations(@RequestParam long userId, @RequestParam(required = false) Integer numberOfRecommendation, @RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude) throws TasteException, SQLException, IOException, ExecutionException, InterruptedException {
        List<LocationEntity> recommendation;
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

}
