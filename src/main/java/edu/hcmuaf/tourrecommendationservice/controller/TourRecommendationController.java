package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.model.Recommendator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private Recommendator recommendator;

    @GetMapping("/get-recommendation")
    public ResponseEntity<List<LocationEntity>> getRecommendation(@RequestParam long userId) throws TasteException, SQLException {
        return new ResponseEntity<List<LocationEntity>>(recommendator.recommend(userId), HttpStatus.OK);
    }
}
