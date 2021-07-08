package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.UserDao;
import edu.hcmuaf.tourrecommendationservice.dto.UserResponse;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;



    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }


    public void save(User user) throws SQLException {
        userDao.save(user);
    }

    public User findUserById(String id) throws SQLException {
        return userDao.findUserById(id);
    }
}
