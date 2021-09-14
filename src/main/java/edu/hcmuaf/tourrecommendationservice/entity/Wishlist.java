package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class Wishlist {
    private User user;
    private List<Location> location;
}
