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

    public User findUserByUsername(String username) {
        User user = null;
        try {
            String sql = "SELECT user_id, name, user_name, password, active, user_image FROM user WHERE user_name = ?";
            PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("active"));
                user.setThumbnail(rs.getString("user_image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO user(name, user_name, password, createAt, active) VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setDate(4, new Date(user.getCreateAt().getTime()));
        preparedStatement.setBoolean(5, user.isActive());
        preparedStatement.executeUpdate();
    }

    public User findUserById(String id) throws SQLException {
        User user = null;

        String sql = "SELECT user_id, user_name, user_image FROM user WHERE user_id = ?";
        PreparedStatement preparedStatement = databaseManager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("user_name"));
            user.setThumbnail(rs.getString("user_image"));
        }
        return user;
    }
}
