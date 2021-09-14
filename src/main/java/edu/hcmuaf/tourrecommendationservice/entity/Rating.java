package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Rating {
private User user;
private Location location;
private float preference;
private String comment;
private Date date;
}
