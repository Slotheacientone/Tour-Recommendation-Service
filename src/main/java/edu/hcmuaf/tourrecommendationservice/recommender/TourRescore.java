package edu.hcmuaf.tourrecommendationservice.recommender;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

import java.util.List;

public class TourRescore implements IDRescorer {
    private List<LocationEntity> locations;
    private LocationRepository locationRepository;

    public TourRescore(List<LocationEntity> locations, LocationRepository locationRepository) {
        this.locations = locations;
        this.locationRepository = locationRepository;
    }

    @SneakyThrows
    @Override
    public double rescore(long itemId, double originalScore) {
        LocationEntity location = locationRepository.getLocation(itemId);
        boolean contains = locations.stream().anyMatch(item -> item.getCategory().contains(location.getCategory()));
        if (contains) {
            return originalScore * 1.2;
        }
        return originalScore;
    }

    @SneakyThrows
    @Override
    public boolean isFiltered(long itemId) {
        return locationRepository.getLocation(itemId) == null;
    }
}
