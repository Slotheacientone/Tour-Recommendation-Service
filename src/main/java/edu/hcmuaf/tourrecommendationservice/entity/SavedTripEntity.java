package edu.hcmuaf.tourrecommendationservice.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SavedTripEntity {
    private long userId;
    private long savedTripId;
    private List<LocationEntity> savedTripLocations;

}
