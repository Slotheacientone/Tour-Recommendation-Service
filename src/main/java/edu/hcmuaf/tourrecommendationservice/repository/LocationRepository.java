package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.LocationDao;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationRepository {

    @Autowired
    private LocationDao locationDao;

    public Location getLocation(long locationId) throws SQLException {
        return locationDao.selectLocation(locationId);
    }

    public void setLatLong(Location location) throws SQLException {
        locationDao.updateLatLong(location);
    }

    public List<Location> filter(String name) throws SQLException {
        return locationDao.filter(name);
    }

    public List<Location> topRating(int limit) throws SQLException {
        return locationDao.topRating(limit);
    }
}
