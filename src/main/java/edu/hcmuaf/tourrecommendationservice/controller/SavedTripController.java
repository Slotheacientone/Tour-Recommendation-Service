package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTripEntity;
import edu.hcmuaf.tourrecommendationservice.service.SavedTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class SavedTripController {

    @Autowired
    private SavedTripService savedTripService;

    @PostMapping("/api/save-trip/save-trip")
    public ResponseEntity<String> saveTrip(@RequestBody SavedTripEntity savedTrip) throws SQLException {
        boolean success = savedTripService.saveTrip(savedTrip);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/save-trip/delete-saved-trip")
    public ResponseEntity<String> deleteSavedTrip(@RequestParam long savedTripId) throws SQLException {
        boolean success = savedTripService.deleteSavedTrip(savedTripId);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/save-trip/get-saved_trip_list")
    public ResponseEntity<List<SavedTripEntity>> getSavedTripList(@RequestParam long userId) throws SQLException {
        List<SavedTripEntity> savedTripEntities = savedTripService.getSavedTripList(userId);
        return new ResponseEntity<>(savedTripEntities, HttpStatus.OK);
    }

    @GetMapping("/api/save-trip/get-saved_trip")
    public ResponseEntity<SavedTripEntity> getSavedTrip(@RequestParam long savedTripId) throws SQLException {
        SavedTripEntity savedTripEntities = savedTripService.getSavedTrip(savedTripId);
        return new ResponseEntity<>(savedTripEntities, HttpStatus.OK);
    }
}