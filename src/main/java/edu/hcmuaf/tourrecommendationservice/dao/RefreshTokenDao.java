package edu.hcmuaf.tourrecommendationservice.dao;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class RefreshTokenDao {

    @Autowired
    private DatabaseManager manager;


    public void save(RefreshToken refreshToken) throws SQLException {
     
        String sql = "INSERT INTO refresh_token VALUES(?,?)";
        PreparedStatement preparedStatement = manager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, refreshToken.getTokenId());
        preparedStatement.setTimestamp(2, Timestamp.from(refreshToken.getExpireAt()));
        preparedStatement.executeUpdate();


    }

    public void delete(String refreshToken) throws SQLException {
        String sql = "DELETE FROM refresh_token WHERE token_id = ?";
        PreparedStatement preparedStatement = manager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, refreshToken);
        preparedStatement.executeUpdate();
    }

    public boolean exists(String tokenId) throws SQLException {
        String sql = "SELECT token_id FROM refresh_token WHERE token_id = ? LIMIT 1";
        PreparedStatement preparedStatement = manager.openConnection().prepareStatement(sql);
        preparedStatement.setString(1, tokenId);
        ResultSet rs = preparedStatement.executeQuery();

        return rs.next();
    }
}
