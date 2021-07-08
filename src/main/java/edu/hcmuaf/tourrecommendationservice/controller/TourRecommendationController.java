package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
import edu.hcmuaf.tourrecommendationservice.service.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TourRecommendationController {

    @Autowired
    private RecommendateService recommendateService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SortRecommendService sortRecommendService;

    @GetMapping("/get-recommendation")
    public ResponseEntity<List<LocationEntity>> getRecommendation(@RequestParam long userId, @RequestParam int numberOfRecommendation) throws TasteException, SQLException {
        return new ResponseEntity<List<LocationEntity>>(recommendateService.recommend(userId, numberOfRecommendation), HttpStatus.OK);
    }

    @PostMapping("/get-recommendation")
    public ResponseEntity<List<LocationEntity>> getRecommendation(@RequestBody long userId, @RequestBody int numberOfRecommendation, @RequestBody double lattitude, @RequestBody double longtitude) throws TasteException, SQLException {
        List<LocationEntity> recommendations = recommendateService.recommend(userId, numberOfRecommendation);
        return new ResponseEntity<List<LocationEntity>>(sortRecommendService.sortByDistance(lattitude,longtitude,recommendations), HttpStatus.OK);
    }

    @PostMapping("/api/comment/register-user-rating")
    public ResponseEntity<String> registerComment(@RequestBody long userId, @RequestBody long locationId, @RequestBody float locationRating, @RequestBody String comment) throws SQLException {
        boolean success = false;
        success = commentService.registerComment(userId,locationId,locationRating,comment);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/comment/get-comments")
    public ResponseEntity<List<CommentEntity>> getComments(@RequestParam long locationId) throws SQLException {
        List<CommentEntity> commentEntities;
        commentEntities = commentService.getComments(locationId);
        return new ResponseEntity<List<CommentEntity>>(commentEntities,HttpStatus.OK);
    }
}
