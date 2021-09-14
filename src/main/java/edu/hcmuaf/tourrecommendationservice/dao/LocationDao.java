package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
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

    public Location selectLocation(long locationId) throws SQLException {
        Location location = null;
        String sql = "select * from location where location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            location = new Location();
            location.setLocationId(locationId);
            location.setLocationName(rs.getString("location_name"));
            location.setLocationImageUrl(rs.getString("location_image"));
            location.setRatings(rs.getFloat("location_rating"));
            location.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            location.setLocationLatitude(rs.getDouble("latitude"));
            location.setLocationLongitude(rs.getDouble("longitude"));
            location.setCategory(rs.getString("location_category"));
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return location;
    }

    public void updateLatLong(Location location) throws SQLException {
        String sql = "update location set latitude=?,longitude=? where location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setDouble(1, location.getLocationLatitude());
        preparedStatement.setDouble(2, location.getLocationLongitude());
        preparedStatement.setLong(3, location.getLocationId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
    }


    public List<Location> filter(String name) throws SQLException {
        List<Location> results = new ArrayList<>();
        String sql = "select * from location where location_name LIKE ?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Location location = new Location();
            location.setLocationId(rs.getLong("location_id"));
            location.setLocationName(rs.getString("location_name"));
            location.setLocationImageUrl(rs.getString("location_image"));
            location.setRatings(rs.getFloat("location_rating"));
            location.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            location.setLocationLatitude(rs.getDouble("latitude"));
            location.setLocationLongitude(rs.getDouble("longitude"));
            location.setCategory(rs.getString("location_category"));
            results.add(location);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return results;
    }

    public List<Location> topRating(int limit) throws SQLException {
        List<Location> results = new ArrayList<>();
        String sql = "select * from location ORDER BY location_rating DESC LIMIT ?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setInt(1, limit);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Location location = new Location();
            location.setLocationId(rs.getLong("location_id"));
            location.setLocationName(rs.getString("location_name"));
            location.setLocationImageUrl(rs.getString("location_image"));
            location.setRatings(rs.getFloat("location_rating"));
            location.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            location.setLocationLatitude(rs.getDouble("latitude"));
            location.setLocationLongitude(rs.getDouble("longitude"));
            location.setCategory(rs.getString("location_category"));
            results.add(location);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return results;
    }
}
