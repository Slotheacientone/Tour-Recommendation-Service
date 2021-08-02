package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.dto.RatingRequest;
import edu.hcmuaf.tourrecommendationservice.entity.RatingEntity;
import edu.hcmuaf.tourrecommendationservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<RatingEntity> getRatings(long locationId) throws SQLException {
        return ratingRepository.getRatings(locationId);
    }

    public boolean registerRating(RatingRequest ratingRequest) throws SQLException {
        return ratingRepository.registerRating(ratingRequest);
    }

    public boolean deleteRating(long userId, long locationId) throws SQLException {
        return ratingRepository.deleteRating(userId,locationId);
    }
}
