package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {

    @Autowired
    private DatabaseManager databaseManager;

    public long insertUser(String userName) throws SQLException {
        String sql = "insert ignore into user (user_name) values (?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
        return selectUserId(userName);
    }

    public void insertUserRating(long userId, long locationId, float rating) throws SQLException {
        String sql = "insert ignore into user_rating (user_id,location_id,preference) values (?,?,?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, locationId);
        preparedStatement.setFloat(3, rating);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseManager.closeConnection();
    }

    public long selectUserId(String userName) throws SQLException {
        long id = 0;
        String sql = "select user_id from user where user_name=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            id = rs.getLong("user_id");
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return id;
    }

    public String selectUserName(long userId) throws SQLException {
        String name = null;
        String sql = "select user_name from user where user_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            name = rs.getString("user_name");
        }
        rs.close();
        preparedStatement.close();
        databaseManager.closeConnection();
        return name;
    }

    public String selectUserRating(long userId) throws SQLException {
        String result = "";
        String sql = "select location_name, preference from user_rating inner join location on user_rating.location_id=location.location_id where user_id=?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            result = result + "(" + rs.getString("location_name") + ":" + rs.getFloat("preference") + ")";
        }
        return result;
    }

    public User findUserByUsername(String username) {
        User user = null;
        try {
            String sql = "SELECT user_id, user_name, password, active FROM user WHERE user_name = ?";
            PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("active"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO user(user_name, password, createAt, active) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setDate(3, new Date(user.getCreateAt().getTime()));
        preparedStatement.setBoolean(4, user.isActive());
        preparedStatement.executeUpdate();
    }
}
