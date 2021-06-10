package edu.hcmuaf.tourrecommendationservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;
}
