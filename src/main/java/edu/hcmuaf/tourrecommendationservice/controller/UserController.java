package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.dto.UserResponse;
import edu.hcmuaf.tourrecommendationservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public UserResponse getInfo(@PathVariable String username) throws SQLException {
        return userService.getInfo(username);
    }
}
