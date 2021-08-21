package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistanceRequest {
    private double latitude;
    private double longitude;
    private List<LocationEntity> locations;
}
