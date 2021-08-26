package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.LocationDao;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationRepository {

    @Autowired
    private LocationDao locationDao;

    public LocationEntity getLocation(long locationId) throws SQLException {
        return locationDao.selectLocation(locationId);
    }

    public void setLatLong(LocationEntity locationEntity) throws SQLException {
        locationDao.updateLatLong(locationEntity);
    }

    public List<LocationEntity> filter(String name) throws SQLException {
        return locationDao.filter(name);
    }

    public List<LocationEntity> topRating(int limit) throws SQLException {
        return locationDao.topRating(limit);
    }
}
