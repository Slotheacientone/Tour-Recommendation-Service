package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
import org.apache.avro.generic.GenericData;
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

//    public long insertLocation(String locationName) throws SQLException {
//        String sql = "insert ignore into location (location_name) values (?)";
//        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
//        preparedStatement.setString(1, locationName);
//        preparedStatement.executeUpdate();
//        preparedStatement.close();
//        databaseManager.closeConnection();
//        return selectLocationId(locationName);
//    }

//    public long selectLocationId(String locationName) throws SQLException {
//        long id = 0;
//        String sql = "select location_id from location where location_name=?";
//        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
//        preparedStatement.setString(1, locationName);
//        ResultSet rs = preparedStatement.executeQuery();
//        if (rs.next()) {
//            id = rs.getLong("location_id");
//        }
//        rs.close();
//        preparedStatement.close();
//        databaseManager.closeConnection();
//        return id;
//    }

//    public String selectLocationName(long locationId) throws SQLException {
//        String name = null;
//        String sql = "select location_name from location where location_id=?";
//        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
//        preparedStatement.setLong(1, locationId);
//        ResultSet rs = preparedStatement.executeQuery();
//        if (rs.next()) {
//            name = rs.getString("location_name");
//        }
//        rs.close();
//        preparedStatement.close();
//        databaseManager.closeConnection();
//        return name;
//    }

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
            locationEntity.setLocationLatitude(rs.getDouble("location_latitude"));
            locationEntity.setLocationLongtitude(rs.getDouble("location_longtitude"));
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return locationEntity;
    }
}
