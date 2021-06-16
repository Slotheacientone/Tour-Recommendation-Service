package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
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

    public List<CommentEntity> getComments(long locationId) throws SQLException {
        return locationRepository.getComments(locationId);
    }
}
