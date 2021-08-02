package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.LocationDao;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class LocationRepository {

    @Autowired
    private LocationDao locationDao;

    public LocationEntity getLocation(long locationId) throws SQLException {
        return locationDao.selectLocation(locationId);
    }

}
