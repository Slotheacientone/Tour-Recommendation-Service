package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;


    public boolean addLocationToWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.addLocationToWishlist(userId, locationId);
    }

    public boolean deleteLocationFromWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.deleteLocationoFromWishlist(userId, locationId);
    }

    public List<LocationEntity> getWishlist(long userId) throws SQLException {
        return wishlistRepository.getWishlist(userId);
    }
}
