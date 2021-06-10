package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.RefreshTokenDao;
import edu.hcmuaf.tourrecommendationservice.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class RefreshTokenRepository {

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    public void save(RefreshToken refreshToken) throws SQLException {
        refreshTokenDao.save(refreshToken);
    }

    public void delete(String refreshToken) throws SQLException {
        refreshTokenDao.delete(refreshToken);
    }

    public boolean exists(String tokenId) throws SQLException {
        return refreshTokenDao.exists(tokenId);
    }
}
