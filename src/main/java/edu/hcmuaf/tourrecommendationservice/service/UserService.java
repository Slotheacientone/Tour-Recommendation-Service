package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.dto.UserResponse;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import edu.hcmuaf.tourrecommendationservice.exception.UserNotFoundException;
import edu.hcmuaf.tourrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public boolean registerUserRating(long userId, long locationId, float locationRating, String comment) throws SQLException {
        return userRepository.registerUserRating(userId, locationId, locationRating, comment);
    }

    public UserResponse getInfo(String username) throws SQLException {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException("Not found user with username: " + username);
        UserResponse response = new UserResponse();
        response.setId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setThumbnail(user.getThumbnail());
        return response;
    }
}
