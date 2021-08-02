package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTripEntity;
import edu.hcmuaf.tourrecommendationservice.repository.SavedTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SavedTripService {

    @Autowired
    private SavedTripRepository savedTripRepository;


    public boolean saveTrip(SavedTripEntity savedTrip) throws SQLException {
        return savedTripRepository.saveTrip(savedTrip);
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        return savedTripRepository.deleteSavedTrip(savedTripId);
    }

    public List<SavedTripEntity> getSavedTripList(long userId) throws SQLException {
        return savedTripRepository.getSavedTripList(userId);
    }

    public SavedTripEntity getSavedTrip(long savedTripId) throws SQLException {
        return savedTripRepository.getSavedTrip(savedTripId);
    }
}
