package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.dto.LocationResponse;
import edu.hcmuaf.tourrecommendationservice.dto.WishlistItemResponse;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.Wishlist;
import edu.hcmuaf.tourrecommendationservice.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;

    @Autowired
    private GeocodingApiService geocodingApiService;


    public boolean addLocationToWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.addLocationToWishlist(userId, locationId);
    }

    public boolean deleteLocationFromWishlist(long userId, long locationId) throws SQLException {
        return wishlistRepository.deleteLocationoFromWishlist(userId, locationId);
    }

    public List<LocationResponse> getWishlist(long userId) throws SQLException {
        List<LocationResponse> wishlistItemResponses = new ArrayList<>();
        Wishlist wishlist = wishlistRepository.getWishlist(userId);
        for(Location location: wishlist.getLocation()){
            WishlistItemResponse wishlistItemResponse = new WishlistItemResponse(location);
            wishlistItemResponses.add(wishlistItemResponse);
        }
        return wishlistItemResponses;
    }

    public List<LocationResponse> getWishlist(long userId, double latitude, double longitude) throws SQLException, IOException {
        List<LocationResponse> wishlistItemResponses = new ArrayList<>();
        Wishlist wishlist = wishlistRepository.getWishlist(userId);
        for(Location location: wishlist.getLocation()){
            WishlistItemResponse wishlistItemResponse = new WishlistItemResponse(location);
            wishlistItemResponses.add(wishlistItemResponse);
        }
        distanceMatrixApiService.calculateDistances(latitude, longitude, wishlistItemResponses);
        return wishlistItemResponses;
    }
}
