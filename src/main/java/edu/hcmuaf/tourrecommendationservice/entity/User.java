package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private long userId;
    private String name;
    private String username;
    private String password;
    private String thumbnail;
    private Date createAt;
    private boolean active;
}
