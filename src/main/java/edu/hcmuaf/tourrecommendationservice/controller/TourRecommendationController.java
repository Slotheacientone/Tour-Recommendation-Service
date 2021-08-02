package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.service.RecommendateService;
import edu.hcmuaf.tourrecommendationservice.service.SortRecommendService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private RecommendateService recommendateService;

    @Autowired
    private SortRecommendService sortRecommendService;

    @GetMapping("/api/recommendate/get-recommendation")
    public ResponseEntity<List<LocationEntity>> getRecommendation(@RequestParam long userId, @RequestParam int numberOfRecommendation) throws TasteException, SQLException {
        return new ResponseEntity<List<LocationEntity>>(recommendateService.recommend(userId, numberOfRecommendation), HttpStatus.OK);
    }

    @PostMapping("/api/recommendate/get-recommendation")
    public ResponseEntity<List<LocationEntity>> getRecommendation(@RequestBody long userId, @RequestBody int numberOfRecommendation, @RequestBody double lattitude, @RequestBody double longtitude) throws TasteException, SQLException {
        List<LocationEntity> recommendations = recommendateService.recommend(userId, numberOfRecommendation);
        return new ResponseEntity<List<LocationEntity>>(sortRecommendService.sortByDistance(lattitude, longtitude, recommendations), HttpStatus.OK);
    }

}
