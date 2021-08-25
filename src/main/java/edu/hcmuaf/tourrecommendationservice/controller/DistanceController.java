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
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class DistanceController {

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;

    @PostMapping("/api/distance/get-distances")
    private ResponseEntity<List<LocationEntity>> getDistances(@RequestBody DistanceRequest distanceRequest) throws IOException, ExecutionException, InterruptedException, SQLException {
        List<LocationEntity> locations = distanceRequest.getLocations();
        System.out.println("run");
        distanceMatrixApiService.calculateDistances(distanceRequest.getLatitude(), distanceRequest.getLongitude(), locations);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
}
