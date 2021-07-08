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

    public UserResponse getInfo(String username) throws SQLException {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException("Not found user with username: " + username);
        UserResponse response = new UserResponse();
        response.setId(user.getUserId());
        response.setName(user.getName());
        response.setThumbnail(user.getThumbnail());
        return response;
    }
}
