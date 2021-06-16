package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUserRating(long userId, long locationId, float rating) throws SQLException {
        return userRepository.registerUserRating(userId,locationId,rating);
    }
}
