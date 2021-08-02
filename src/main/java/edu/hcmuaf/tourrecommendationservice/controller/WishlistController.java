package edu.hcmuaf.tourrecommendationservice.controller;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/api/wishlist/add-location-to-wishlist")
    public ResponseEntity<String> addLocationToWishlist(@RequestParam long userId, @RequestParam long locationId) throws SQLException {
        boolean success = wishlistService.addLocationToWishlist(userId,locationId);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/wishlist/delete-location-from-wishlist")
    public ResponseEntity<String> deleteLocationoFromWishlist(@RequestParam long userId, @RequestParam long locationId) throws SQLException {
        boolean success = wishlistService.deleteLocationFromWishlist(userId,locationId);
        if(success){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/wishlist/get-wishlist")
    public ResponseEntity<List<LocationEntity>> getWishlist(@RequestParam long userId) throws SQLException {
        List<LocationEntity> locationEntities = wishlistService.getWishlist(userId);
        return new ResponseEntity<>(locationEntities, HttpStatus.OK);
    }
}
