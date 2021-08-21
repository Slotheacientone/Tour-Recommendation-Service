package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;


    public boolean addLocationToWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.addLocationToWishlist(userId, locationId);
    }

    public boolean deleteLocationFromWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.deleteLocationoFromWishlist(userId, locationId);
    }

    public List<LocationEntity> getWishlist(long userId) throws SQLException {
        return wishlistRepository.getWishlist(userId);
    }

    public List<LocationEntity> getWishlist(long userId, double latitude, double longitude) throws SQLException, IOException, ExecutionException, InterruptedException {
        List<LocationEntity> wishlist = wishlistRepository.getWishlist(userId);
        for (LocationEntity locationEntity: wishlist){
            int distance = -1;
            if (locationEntity.getLocationLatitude() != 0 && locationEntity.getLocationLongitude() != 0) {
                distance = distanceMatrixApiService.calculateDistance(latitude, longitude, locationEntity.getLocationLatitude(), locationEntity.getLocationLongitude());
            } else {
                distance = distanceMatrixApiService.calculateDistance(latitude, longitude, locationEntity.getLocationName());
            }
            locationEntity.setDistance(distance);
        }
        return wishlist;
    }
}
