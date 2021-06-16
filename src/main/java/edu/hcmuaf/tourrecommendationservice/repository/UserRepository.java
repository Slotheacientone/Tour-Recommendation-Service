package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.UserDao;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public boolean registerUserRating(long userId, long locationId, float locationRating, String comment) throws SQLException {
        return userDao.insertUserRating(userId,locationId,locationRating,comment);
    }

    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }


    public void save(User user) throws SQLException {
        userDao.save(user);
    }
}
