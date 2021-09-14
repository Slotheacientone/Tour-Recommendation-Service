package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTrip;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SavedTripDao {

    @Autowired
    private DatabaseManager databaseManager;

    public boolean insertSavedTrip(SavedTrip savedTrip) throws SQLException {
        int rowAffected;
        long savedTripId;
        String insertSavedTripSql = "insert ignore into saved_trip (user_id) values (?)";
        Connection connection = databaseManager.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertSavedTripSql);
        preparedStatement.setLong(1, savedTrip.getUser().getUserId());
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        String getLastInsertIdSql = "select last_insert_id()";
        ResultSet rs = connection.createStatement().executeQuery(getLastInsertIdSql);
        if (rs.next()) {
            savedTripId = rs.getLong(1);
            insertLocationToSavedTrip(savedTripId, savedTrip.getSavedTripLocations());
        }
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public void insertLocationToSavedTrip(long savedTripId, List<Location> locationEntities) throws SQLException {
        String sql = "insert ignore into saved_trip_location (saved_trip_id,location_id) values (?,?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        for (Location location : locationEntities) {
            preparedStatement.setLong(1, savedTripId);
            preparedStatement.setLong(2, location.getLocationId());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        databaseManager.closeConnection();
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        int rowAffected;
        String deleteSavedTripSql = "delete saved_trip, saved_trip_location" +
                " from saved_trip" +
                " left join saved_trip_location" +
                " on saved_trip.saved_trip_id = saved_trip_location.saved_trip_id" +
                " where saved_trip.saved_trip_id=?";
        Connection connection = databaseManager.openConnection();
        PreparedStatement deleteSavedTripPreparedStatement = connection.prepareStatement(deleteSavedTripSql);
        deleteSavedTripPreparedStatement.setLong(1, savedTripId);
        rowAffected = deleteSavedTripPreparedStatement.executeUpdate();
        deleteSavedTripPreparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public List<SavedTrip> selectAllSavedTrip(long userId) throws SQLException {
        List<SavedTrip> savedTrips = new ArrayList<>();
        String sql = "select * from saved_trip where user_id =?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            SavedTrip savedTrip = new SavedTrip();
            long savedTripId = rs.getLong("saved_trip_id");
            List<Location> locationEntities = selectAllSavedTripLocation(savedTripId);
            savedTrip.setSavedTripLocations(locationEntities);
            savedTrip.setSavedTripId(savedTripId);
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            savedTrip.setUser(user);
            savedTrips.add(savedTrip);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return savedTrips;
    }

    public List<Location> selectAllSavedTripLocation(long savedTripId) throws SQLException {
        List<Location> locationEntities = new ArrayList<>();
        String sql = "select * from saved_trip_location inner join location " +
                "on saved_trip_location.location_id=location.location_id where saved_trip_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, savedTripId);
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
            locationEntities.add(location);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return locationEntities;
    }

    public SavedTrip selectSavedTrip(long savedTripId) throws SQLException {
        SavedTrip savedTrip = null;
        String sql = "select * from saved_trip where saved_trip_id =?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, savedTripId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            savedTrip = new SavedTrip();
            List<Location> locationEntities = selectAllSavedTripLocation(savedTripId);
            savedTrip.setSavedTripLocations(locationEntities);
            savedTrip.setSavedTripId(savedTripId);
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            savedTrip.setUser(user);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return savedTrip;
    }

    public int getSavedTripLocationCount(long savedTripId) throws SQLException {
        int count;
        String sql = "select count(*) from saved_trip_location where saved_trip_id =?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, savedTripId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        count = rs.getInt(1);
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return count;
    }

    public boolean deleteLocationFromSavedTrip(long savedTripId, long locationId) throws SQLException {
        int rowAffected;
        String deleteLocationFromSavedTripSql = "delete from saved_trip_location where saved_trip_id=? and location_id=?";
        PreparedStatement deleteSavedTripPreparedStatement = databaseManager.openConnection().prepareStatement(deleteLocationFromSavedTripSql);
        deleteSavedTripPreparedStatement.setLong(1, savedTripId);
        deleteSavedTripPreparedStatement.setLong(2, locationId);
        rowAffected = deleteSavedTripPreparedStatement.executeUpdate();
        deleteSavedTripPreparedStatement.close();
        databaseManager.closeConnection();
        if(getSavedTripLocationCount(savedTripId) == 0){
            deleteSavedTrip(savedTripId);
        }
        return rowAffected > 0;
    }
}
