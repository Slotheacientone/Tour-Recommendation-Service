package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.dto.RatingRequest;
import edu.hcmuaf.tourrecommendationservice.entity.RatingEntity;
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

    public List<RatingEntity> selectAllRating(long locationId) throws SQLException {
        List<RatingEntity> ratingEntities = new ArrayList<>();
        String sql = "select * from user_rating inner join user on user_rating.user_id=user.user_id where location_id=? and comment is not null";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            RatingEntity ratingEntity = new RatingEntity();
            ratingEntity.setUserId(rs.getLong("user_id"));
            ratingEntity.setUserName(rs.getString("name"));
            ratingEntity.setUserImageUrl(rs.getString("user_image"));
            ratingEntity.setRating(rs.getFloat("preference"));
            ratingEntity.setComment(rs.getString("comment"));
            ratingEntity.setDate(rs.getDate("rating_date"));
            ratingEntities.add(ratingEntity);
        }
        return ratingEntities;
    }

    public boolean insertRating(RatingRequest ratingRequest) throws SQLException {
        int rowAffected = 0;
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
        int rowAffected = 0;
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
