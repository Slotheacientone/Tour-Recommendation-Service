package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.dto.*;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import edu.hcmuaf.tourrecommendationservice.exception.UserAlreadyExistsException;
import edu.hcmuaf.tourrecommendationservice.repository.UserRepository;
import edu.hcmuaf.tourrecommendationservice.security.JwtProvider;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest request) {
        try {
            User user = new User();
            user.setName(request.getName());
            user.setUsername(request.getUsername());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setCreateAt(Date.from(Instant.now()));
            user.setActive(true);
            userRepository.save(user);
        } catch (SQLException ex) {
            throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());
        }

    }

    public AuthenticationResponse login(LoginRequest request) throws SQLException {
        Authentication authenticate;
        try {
            Authentication authReq = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticate = authenticationManager.authenticate(authReq);
        } catch (DisabledException ex) {
            throw new DisabledException("USER DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("BAD CREDENTIALS", ex);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final String token = jwtProvider.generateAccessToken(authenticate);
        final String refreshToken = refreshTokenService.generate(request.getUsername());

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }


    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        final String refreshToken = request.getRefreshToken();

        refreshTokenService.validate(refreshToken);

        final String username = jwtProvider.parseUsernameFromJwt(refreshToken);
        final String accessToken = jwtProvider.generateAccessToken(username);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .expireAt(Instant.now().plus(jwtProvider.getRefreshTokenExpire(), ChronoUnit.SECONDS))
                .build();
    }

    public void logout(RefreshTokenRequest request) {
        refreshTokenService.delete(request.getRefreshToken());
    }
}
