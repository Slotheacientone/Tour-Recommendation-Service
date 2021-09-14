package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.SavedTripDao;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class SavedTripRepository {

    @Autowired
    private SavedTripDao savedTripDao;

    public boolean saveTrip(SavedTrip savedTrip) throws SQLException {
        return savedTripDao.insertSavedTrip(savedTrip);
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        return savedTripDao.deleteSavedTrip(savedTripId);
    }

    public List<SavedTrip> getSavedTrips(long userId) throws SQLException {
        return savedTripDao.selectAllSavedTrip(userId);
    }

    public SavedTrip getSavedTrip(long savedTripId) throws SQLException {
        return savedTripDao.selectSavedTrip(savedTripId);
    }

    public boolean deleteLocationFromSavedTrip(long savedTripId, long locationId) throws SQLException {
        return savedTripDao.deleteLocationFromSavedTrip(savedTripId,locationId);
    }
}
