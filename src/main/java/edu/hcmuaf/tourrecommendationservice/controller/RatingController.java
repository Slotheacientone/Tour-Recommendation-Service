package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.dto.RatingRequest;
import edu.hcmuaf.tourrecommendationservice.dto.RatingResponse;
import edu.hcmuaf.tourrecommendationservice.entity.Rating;
import edu.hcmuaf.tourrecommendationservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/api/rating/register-user-rating")
    public ResponseEntity<String> registerRating(@RequestBody RatingRequest ratingRequest) throws SQLException {
        boolean success;
        success = ratingService.registerRating(ratingRequest);
        if(success){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/rating/delete-rating")
    public ResponseEntity<String> deleteRating(@RequestParam long userId, @RequestParam long locationId) throws SQLException {
        boolean success;
        success = ratingService.deleteRating(userId,locationId);
        if(success){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/rating/get-ratings")
    public ResponseEntity<List<RatingResponse>> getRatings(@RequestParam long locationId) throws SQLException {
        List<RatingResponse> ratingEntities;
        ratingEntities = ratingService.getRatings(locationId);
        return new ResponseEntity<>(ratingEntities, HttpStatus.OK);
    }
}
