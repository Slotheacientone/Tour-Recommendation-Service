package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationEntity> getTopRating(@RequestParam int limit) throws SQLException {
        return locationService.topRating(limit);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationEntity> filter(@RequestParam(required = false) String name) throws SQLException {
        return locationService.filter(name);
    }

}
