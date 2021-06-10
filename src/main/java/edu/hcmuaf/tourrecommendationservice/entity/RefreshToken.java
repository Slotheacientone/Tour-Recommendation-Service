package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    private String tokenId;
    private Instant expireAt;
}
