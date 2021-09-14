package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.WishlistDao;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class WishlistRepository {

    @Autowired
    private WishlistDao wishlistDao;

    public boolean addLocationToWishlist(long userId, long locationId) throws SQLException {
        return wishlistDao.insertLocationToWishlist(userId,locationId);
    }

    public boolean deleteLocationoFromWishlist(long userId, long locationId) throws SQLException {
        return wishlistDao.deleteLocationFromWishlist(userId,locationId);
    }

    public Wishlist getWishlist(long userId) throws SQLException {
        return wishlistDao.selectAllLocationFromWishlist(userId);
    }
}
