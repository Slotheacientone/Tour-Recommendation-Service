package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.dto.RatingRequest;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.Rating;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RatingDao {

    @Autowired
    private DatabaseManager databaseManager;

    public List<Rating> selectAllRating(long locationId) throws SQLException {
        List<Rating> ratingEntities = new ArrayList<>();
        String sql = "select * from user_rating inner join user on user_rating.user_id=user.user_id where location_id=? and comment is not null";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rating rating = new Rating();
            Location location = new Location();
            location.setLocationId(locationId);
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("name"));
            user.setThumbnail(rs.getString("user_image"));
            rating.setPreference(rs.getFloat("preference"));
            rating.setComment(rs.getString("comment"));
            rating.setDate(rs.getDate("rating_date"));
            rating.setUser(user);
            rating.setLocation(location);
            ratingEntities.add(rating);
        }
        return ratingEntities;
    }

    public boolean insertRating(RatingRequest ratingRequest) throws SQLException {
        int rowAffected;
        String sql = "insert ignore into user_rating (user_id,location_id,preference,comment, rating_date) values (?,?,?,?,sysdate()) " +
                "on duplicate key update preference=?,comment=?,rating_date=sysdate()";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, ratingRequest.getUserId());
        preparedStatement.setLong(2, ratingRequest.getLocationId());
        preparedStatement.setFloat(3, ratingRequest.getRating());
        preparedStatement.setString(4, ratingRequest.getComment());
        preparedStatement.setFloat(5, ratingRequest.getRating());
        preparedStatement.setString(6, ratingRequest.getComment());
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }

    public boolean deleteRating(long userId, long locationId) throws SQLException {
        int rowAffected;
        String sql = "delete from user_rating where user_id=? and location_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        rowAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }
}
