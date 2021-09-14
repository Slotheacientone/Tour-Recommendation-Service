package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location getLocation(long locationId) throws SQLException {
        return locationRepository.getLocation(locationId);
    }

    public void setLatLong(Location location) throws SQLException {
        locationRepository.setLatLong(location);
    }

    public List<Location> filter(String name) throws SQLException {
        return locationRepository.filter(name);
    }

    public List<Location> topRating(int limit) throws SQLException {
        return locationRepository.topRating(limit);
    }
}
