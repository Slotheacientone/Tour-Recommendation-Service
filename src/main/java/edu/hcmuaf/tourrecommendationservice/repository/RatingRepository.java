package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.RatingDao;
import edu.hcmuaf.tourrecommendationservice.dto.RatingRequest;
import edu.hcmuaf.tourrecommendationservice.entity.RatingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class RatingRepository {

    @Autowired
    private RatingDao ratingDao;

    public List<RatingEntity> getRatings(long locationId) throws SQLException {
        return ratingDao.selectAllRating(locationId);
    }

    public boolean registerRating(RatingRequest ratingRequest) throws SQLException {
        return ratingDao.insertRating(ratingRequest);
    }

    public boolean deleteRating(long userId, long locationId) throws SQLException {
        return ratingDao.deleteRating(userId,locationId);
    }
}
