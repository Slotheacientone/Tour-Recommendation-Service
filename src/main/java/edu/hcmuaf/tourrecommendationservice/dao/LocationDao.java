package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocationDao {

    @Autowired
    private DatabaseManager databaseManager;

    public LocationEntity selectLocation(long locationId) throws SQLException {
        LocationEntity locationEntity = null;
        String sql = "select * from location where location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            locationEntity = new LocationEntity();
            locationEntity.setLocationId(locationId);
            locationEntity.setLocationName(rs.getString("location_name"));
            locationEntity.setLocationImageUrl(rs.getString("location_image"));
            locationEntity.setRatings(rs.getFloat("location_rating"));
            locationEntity.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            locationEntity.setLocationLatitude(rs.getDouble("latitude"));
            locationEntity.setLocationLongitude(rs.getDouble("longitude"));
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return locationEntity;
    }

    public void updateLatLong(LocationEntity location) throws SQLException {
        String sql = "update location set latitude=?,longitude=? where location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setDouble(1, location.getLocationLatitude());
        preparedStatement.setDouble(2, location.getLocationLongitude());
        preparedStatement.setLong(3, location.getLocationId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
    }


    public List<LocationEntity> filter(String name) throws SQLException {
        List<LocationEntity> results = new ArrayList<>();
        String sql = "select * from location where location_name LIKE ?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setLocationId(rs.getLong("location_id"));
            locationEntity.setLocationName(rs.getString("location_name"));
            locationEntity.setLocationImageUrl(rs.getString("location_image"));
            locationEntity.setRatings(rs.getFloat("location_rating"));
            locationEntity.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            locationEntity.setLocationLatitude(rs.getDouble("latitude"));
            locationEntity.setLocationLongitude(rs.getDouble("longitude"));
            results.add(locationEntity);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return results;
    }

    public List<LocationEntity> topRating(int limit) throws SQLException {
        List<LocationEntity> results = new ArrayList<>();
        String sql = "select * from location ORDER BY location_rating DESC LIMIT ?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setInt(1, limit);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setLocationId(rs.getLong("location_id"));
            locationEntity.setLocationName(rs.getString("location_name"));
            locationEntity.setLocationImageUrl(rs.getString("location_image"));
            locationEntity.setRatings(rs.getFloat("location_rating"));
            locationEntity.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            locationEntity.setLocationLatitude(rs.getDouble("latitude"));
            locationEntity.setLocationLongitude(rs.getDouble("longitude"));
            results.add(locationEntity);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return results;
    }
}
