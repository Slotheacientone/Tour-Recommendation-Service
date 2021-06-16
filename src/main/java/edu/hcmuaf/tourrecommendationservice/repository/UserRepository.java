package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public boolean registerUserRating(long userId, long locationId, float rating) throws SQLException {
        return userDao.insertUserRating(userId,locationId,rating);
    }
}
