package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LocationDao {

    @Autowired
    private DatabaseManager databaseManager;

    public long insertLocation(String locationName) throws SQLException {
        String sql = "insert ignore into location (location_name) values (?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, locationName);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return selectLocationId(locationName);
    }

    public long selectLocationId(String locationName) throws SQLException {
        long id = 0;
        String sql = "select location_id from location where location_name=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, locationName);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            id  = rs.getLong("location_id");
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return id;
    }

    public String selectLocationName(long locationId) throws SQLException {
        String name = null;
        String sql ="select location_name from location where location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            name = rs.getString("location_name");
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return name;
    }
}
