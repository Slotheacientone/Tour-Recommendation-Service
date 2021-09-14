package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import edu.hcmuaf.tourrecommendationservice.entity.Wishlist;
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
        int rowAffected;
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
        int rowAffected;
        String sql = "delete from wishlist where user_id=? and location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public Wishlist selectAllLocationFromWishlist(long userId) throws SQLException {
        List<Location> locations = new ArrayList<>();
        String sql = "select * from wishlist inner join location on wishlist.location_id=location.location_id where user_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
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
            locations.add(location);
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        Wishlist wishlist = new Wishlist();
        User user = new User();
        user.setUserId(userId);
        wishlist.setLocation(locations);
        wishlist.setUser(user);
        return wishlist;
    }
}
