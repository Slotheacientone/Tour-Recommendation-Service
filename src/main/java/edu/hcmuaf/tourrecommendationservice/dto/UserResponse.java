package edu.hcmuaf.tourrecommendationservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String name;
    private String thumbnail;
}
