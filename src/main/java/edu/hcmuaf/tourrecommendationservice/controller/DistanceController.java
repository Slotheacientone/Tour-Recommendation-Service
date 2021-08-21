package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.dto.DistanceRequest;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.service.DistanceMatrixApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class DistanceController {

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;

    @PostMapping("/api/distance/get-distances")
    private ResponseEntity<List<LocationEntity>> getDistances(@RequestBody DistanceRequest distanceRequest) throws IOException, ExecutionException, InterruptedException {
        List<LocationEntity> locations =distanceRequest.getLocations();
        for(LocationEntity locationEntity: locations) {
            int distance = -1;
            if (locationEntity.getLocationLatitude() != 0 && locationEntity.getLocationLongitude() != 0) {
                distance = distanceMatrixApiService.calculateDistance(distanceRequest.getLatitude(), distanceRequest.getLongitude(), locationEntity.getLocationLatitude(), locationEntity.getLocationLongitude());
            } else {
                distance = distanceMatrixApiService.calculateDistance(distanceRequest.getLatitude(), distanceRequest.getLongitude(), locationEntity.getLocationName());
            }
            locationEntity.setDistance(distance);
        }
        return new ResponseEntity<List<LocationEntity>>(locations, HttpStatus.OK);
    }
}
