package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationEntity getLocation(long locationId) throws SQLException {
        return locationRepository.getLocation(locationId);
    }

    public void setLatLong(LocationEntity locationEntity) throws SQLException {
        locationRepository.setLatLong(locationEntity);
    }

    public List<LocationEntity> filter(String name) throws SQLException {
        return locationRepository.filter(name);
    }

    public List<LocationEntity> topRating(int limit) throws SQLException {
        return locationRepository.topRating(limit);
    }
}
