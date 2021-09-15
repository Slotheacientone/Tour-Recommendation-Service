package edu.hcmuaf.tourrecommendationservice.recommender;

import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

import java.util.List;

public class TourRescore implements IDRescorer {
    private List<Location> locations;
    private LocationRepository locationRepository;

    public TourRescore(List<Location> locations, LocationRepository locationRepository) {
        this.locations = locations;
        this.locationRepository = locationRepository;
    }

    @SneakyThrows
    @Override
    public double rescore(long itemId, double originalScore) {
        return originalScore;
    }

    @SneakyThrows
    @Override
    public boolean isFiltered(long itemId) {
        return locationRepository.getLocation(itemId) == null;
    }
}
