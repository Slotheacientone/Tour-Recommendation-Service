package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.dto.*;

import edu.hcmuaf.tourrecommendationservice.service.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest request) {
        authService.signup(request);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) throws SQLException {
        return authService.login(request);
    }

    @PostMapping("/refreshToken")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().body("Refresh Token Deleted Successfully!!");
    }


}
