package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.RefreshToken;
import edu.hcmuaf.tourrecommendationservice.exception.TokenInvalidException;
import edu.hcmuaf.tourrecommendationservice.repository.RefreshTokenRepository;
import edu.hcmuaf.tourrecommendationservice.security.JwtProvider;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public String generate(String username) throws SQLException {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTokenId(UUID.randomUUID().toString());
        refreshToken.setExpireAt(Instant.now().plus(jwtProvider.getRefreshTokenExpire(), ChronoUnit.SECONDS));
        refreshTokenRepository.save(refreshToken);

        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", refreshToken.getTokenId());
        map.put("username", username);
        return jwtProvider.generateRefreshToken(map);
    }


    public void delete(String refreshToken) {
        try {
            String tokenId = jwtProvider.parseTokenIdFromJwt(refreshToken);
            validate(refreshToken);
            refreshTokenRepository.delete(tokenId);
        } catch (SQLException ex) {
            log.error(ex.getLocalizedMessage());
        }

    }

    public void validate(String refreshToken) {
        String tokenId = jwtProvider.parseTokenIdFromJwt(refreshToken);
        try {
            if (!refreshTokenRepository.exists(tokenId)) {
                throw new TokenInvalidException("REFRESH_TOKEN INVALID");
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }
}
