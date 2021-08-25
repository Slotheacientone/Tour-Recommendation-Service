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
public class WishlistDao {

    @Autowired
    private DatabaseManager databaseManager;

    public boolean insertLocationToWishlist(long userId, long locationId) throws SQLException {
        int rowAffected = 0;
        String sql = "insert ignore into wishlist (user_id,location_id) values (?,?) ";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public boolean deleteLocationFromWishlist(long userId, long locationId) throws SQLException {
        int rowAffected = 0;
        String sql = "delete from wishlist where user_id=? and location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public List<LocationEntity> selectAllLocationFromWishlist(long userId) throws SQLException {
        int wishListOrder =0;
        List<LocationEntity> locationEntities = new ArrayList<>();
        String sql = "select * from wishlist inner join location on wishlist.location_id=location.location_id where user_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setLocationId(rs.getLong("location_id"));
            locationEntity.setLocationName(rs.getString("location_name"));
            locationEntity.setLocationImageUrl(rs.getString("location_image"));
            locationEntity.setRatings(rs.getFloat("location_rating"));
            locationEntity.setNumberOfPeopleRating(rs.getInt("number_people_rating"));
            locationEntity.setLocationLatitude(rs.getDouble("latitude"));
            locationEntity.setLocationLongitude(rs.getDouble("longitude"));
            wishListOrder++;
            locationEntity.setWishListOrder(wishListOrder);
            locationEntities.add(locationEntity);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return locationEntities;
    }
}
