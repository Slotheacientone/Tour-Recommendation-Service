package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTripEntity;
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

    public boolean insertSavedTrip(SavedTripEntity savedTrip) throws SQLException {
        int rowAffected = 0;
        long savedTripId = 0;
        String insertSavedTripSql = "insert ignore into saved_trip (user_id) values (?)";
        Connection connection = databaseManager.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertSavedTripSql);
        preparedStatement.setLong(1, savedTrip.getUserId());
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

    public void insertLocationToSavedTrip(long savedTripId, List<LocationEntity> locationEntities) throws SQLException {
        String sql = "insert ignore into saved_trip_location (saved_trip_id,location_id) values (?,?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        for (LocationEntity locationEntity : locationEntities) {
            preparedStatement.setLong(1, savedTripId);
            preparedStatement.setLong(2, locationEntity.getLocationId());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        databaseManager.closeConnection();
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        int rowAffected = 0;
        String deleteSavedTripSql = "delete from saved_trip where saved_trip_id=?";
        Connection connection = databaseManager.openConnection();
        PreparedStatement deleteSavedTripPreparedStatement = connection.prepareStatement(deleteSavedTripSql);
        deleteSavedTripPreparedStatement.setLong(1, savedTripId);
        rowAffected = deleteSavedTripPreparedStatement.executeUpdate();
        deleteSavedTripPreparedStatement.close();
        String deleteSavedTripLocationSql = "delete from saved_trip where saved_trip_id=?";
        PreparedStatement deleteSavedTripLocationPreparedStatement = connection.prepareStatement(deleteSavedTripLocationSql);
        deleteSavedTripLocationPreparedStatement.setLong(1, savedTripId);
        deleteSavedTripLocationPreparedStatement.executeUpdate();
        deleteSavedTripLocationPreparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public List<SavedTripEntity> selectAllSavedTrip(long userId) throws SQLException {
        List<SavedTripEntity> SavedTripEntities = new ArrayList<>();
        String sql = "select * from saved_trip where user_id =?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            SavedTripEntity savedTripEntity = new SavedTripEntity();
            long savedTripId = rs.getLong("saved_trip_id");
            List<LocationEntity> locationEntities = selectAllSavedTripLocation(savedTripId);
            savedTripEntity.setSavedTripLocations(locationEntities);
            savedTripEntity.setSavedTripId(savedTripId);
            savedTripEntity.setUserId(rs.getLong("user_id"));
            SavedTripEntities.add(savedTripEntity);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return SavedTripEntities;
    }

    public List<LocationEntity> selectAllSavedTripLocation(long savedTripId) throws SQLException {
        List<LocationEntity> locationEntities = new ArrayList<>();
        String sql = "select * from saved_trip_location inner join location " +
                "on saved_trip_location.location_id=location.location_id where saved_trip_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, savedTripId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setLocationId(rs.getLong("location_id"));
            locationEntity.setLocationName(rs.getString("location_name"));
            locationEntity.setLocationImageUrl(rs.getString("location_image"));
            locationEntity.setRatings(rs.getFloat("location_rating"));
            locationEntity.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            locationEntity.setLocationLatitude(rs.getDouble("latitude"));
            locationEntity.setLocationLongitude(rs.getDouble("longtitude"));
            locationEntities.add(locationEntity);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return locationEntities;
    }

    public SavedTripEntity selectSavedTrip(long savedTripId) throws SQLException {
        SavedTripEntity savedTripEntity = null;
        String sql = "select * from saved_trip where saved_trip_id =?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, savedTripId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            savedTripEntity = new SavedTripEntity();
            List<LocationEntity> locationEntities = selectAllSavedTripLocation(savedTripId);
            savedTripEntity.setSavedTripLocations(locationEntities);
            savedTripEntity.setSavedTripId(savedTripId);
            savedTripEntity.setUserId(rs.getLong("user_id"));
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return savedTripEntity;
    }
}
