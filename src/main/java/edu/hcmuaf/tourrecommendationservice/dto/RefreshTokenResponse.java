package edu.hcmuaf.tourrecommendationservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RefreshTokenResponse {
    private String accessToken;
    private Instant expireAt;
}
