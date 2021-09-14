package edu.hcmuaf.tourrecommendationservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String username;
    private String name;
    private String thumbnail;
}
