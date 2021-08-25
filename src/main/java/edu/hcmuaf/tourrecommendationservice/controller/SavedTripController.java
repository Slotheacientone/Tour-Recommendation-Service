package edu.hcmuaf.tourrecommendationservice.controller;

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
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/save-trip/delete-saved-trip")
    public ResponseEntity<String> deleteSavedTrip(@RequestParam long savedTripId) throws SQLException {
        boolean success = savedTripService.deleteSavedTrip(savedTripId);
        if(success){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/save-trip/get-saved-trips")
    public ResponseEntity<List<SavedTripEntity>> getSavedTrips(@RequestParam long userId) throws SQLException {
        List<SavedTripEntity> savedTripEntities = savedTripService.getSavedTrips(userId);
        return new ResponseEntity<>(savedTripEntities, HttpStatus.OK);
    }

    @GetMapping("/api/save-trip/get-saved-trip")
    public ResponseEntity<SavedTripEntity> getSavedTrip(@RequestParam long savedTripId) throws SQLException {
        SavedTripEntity savedTripEntities = savedTripService.getSavedTrip(savedTripId);
        return new ResponseEntity<>(savedTripEntities, HttpStatus.OK);
    }

    @GetMapping("/api/save-trip/delete-location-from-saved-trip")
    public ResponseEntity<String> deleteLocationFromSavedTrip(@RequestParam long savedTripId, @RequestParam long locationId) throws SQLException {
        boolean isSuccess = savedTripService.deleteLocationFromSavedTrip(savedTripId, locationId);
        if(isSuccess){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
