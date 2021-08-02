package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.SavedTripDao;
import edu.hcmuaf.tourrecommendationservice.dao.WishlistDao;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTripEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class SavedTripRepository {

    @Autowired
    private SavedTripDao savedTripDao;

    public boolean saveTrip(SavedTripEntity savedTrip) throws SQLException {
        return savedTripDao.insertSavedTrip(savedTrip);
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        return savedTripDao.deleteSavedTrip(savedTripId);
    }

    public List<SavedTripEntity> getSavedTripList(long userId) throws SQLException {
        return savedTripDao.selectAllSavedTrip(userId);
    }

    public SavedTripEntity getSavedTrip(long savedTripId) throws SQLException {
        return savedTripDao.selectSavedTrip(savedTripId);
    }
}
