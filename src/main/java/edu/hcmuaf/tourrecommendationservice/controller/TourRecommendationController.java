package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.RecommendationEntity;
import edu.hcmuaf.tourrecommendationservice.model.Recommendator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private Recommendator recommendator;

    @GetMapping("/get-recommendation")
    public ResponseEntity<List<RecommendationEntity>> getRecommendation(@RequestParam String userId) throws TasteException {

        recommendator.recommend(12345);
        return null;
    }
}
