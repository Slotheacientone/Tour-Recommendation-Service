package edu.hcmuaf.tourrecommendationservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JwtProvider {

    @Value("${jwt.ACCESS_TOKEN_SECRET}")
    private byte[] ACCESS_TOKEN_SECRET;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.accessToken.expiration.time}")
    private long accessTokenExpire;

    @Value("${jwt.refreshToken.expiration.time}")
    private long refreshTokenExpire;


    public String generateAccessToken(Authentication auth) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject("ACCESS_TOKEN")
                .claim("username", auth.getName())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(accessTokenExpire, ChronoUnit.SECONDS)))
                .signWith(getSecretKey())
                .compact();

    }

    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject("ACCESS_TOKEN")
                .claim("username", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(accessTokenExpire, ChronoUnit.SECONDS)))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> map) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuer(issuer)
                .addClaims(map)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(refreshTokenExpire, ChronoUnit.SECONDS)))
                .signWith(getSecretKey())
                .compact();
    }


    public boolean validateToken(String token) {
        Jwts.parser()
                .requireSubject("ACCESS_TOKEN")
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token);
        return true;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET);
    }

    public String parseUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public long getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public String parseTokenIdFromJwt(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(refreshToken)
                .getBody()
                .get("tokenId", String.class);
    }
}
