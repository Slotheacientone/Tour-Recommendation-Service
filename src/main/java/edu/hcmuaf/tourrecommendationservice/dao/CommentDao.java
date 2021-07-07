package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDao {

    @Autowired
    private DatabaseManager databaseManager;

    public List<CommentEntity> selectAllComment(long locationId) throws SQLException {
        List<CommentEntity> commentEntities = new ArrayList<>();
        String sql = "select * from user_rating inner join user on user_rating.user_id=user.user_id where location_id=? and comment is not null";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, locationId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUserId(rs.getLong("user_id"));
            commentEntity.setUserName(rs.getString("name"));
            commentEntity.setUserImageUrl(rs.getString("user_image"));
            commentEntity.setRating(rs.getFloat("preference"));
            commentEntity.setComment(rs.getString("comment"));
            commentEntity.setDate(rs.getDate("rating_date"));
            commentEntities.add(commentEntity);
        }
        return commentEntities;
    }

    public boolean insertComment(long userId, long locationId, float locationRating, String comment) throws SQLException {
        int rowAffected = 0;
        String sql = "insert ignore into user_rating (user_id,location_id,preference,comment, date) values (?,?,?,?,sysdate())";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        preparedStatement.setFloat(3, locationRating);
        preparedStatement.setString(4, comment);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return rowAffected > 0;
    }
}
