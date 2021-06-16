package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
import edu.hcmuaf.tourrecommendationservice.service.LocationService;
import edu.hcmuaf.tourrecommendationservice.service.RecommendateService;
import edu.hcmuaf.tourrecommendationservice.service.UserService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private RecommendateService recommendateService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/get-recommendation")
    public ResponseEntity<List<RecommendEntity>> getRecommendation(@RequestParam long userId, @RequestParam int numberOfRecommendation) throws TasteException, SQLException {
        return new ResponseEntity<List<RecommendEntity>>(recommendateService.recommend(userId, numberOfRecommendation), HttpStatus.OK);
    }

    @GetMapping("/register-user-rating")
    public ResponseEntity<String> registerUserRating(@RequestParam long userId, long locationId, float rating) throws SQLException {
        boolean success = false;
        success = userService.registerUserRating(userId,locationId,rating);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-comments")
    public ResponseEntity<List<CommentEntity>> getComments(@RequestParam long locationId) throws SQLException {
        List<CommentEntity> commentEntities;
        commentEntities = locationService.getComments(locationId);
        return  new ResponseEntity<List<CommentEntity>>(commentEntities,HttpStatus.OK);
    }
}
