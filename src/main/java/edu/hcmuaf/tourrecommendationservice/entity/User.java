package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String userId;
    private String username;
    private String password;
    private Date createAt;
    private boolean active;
}
