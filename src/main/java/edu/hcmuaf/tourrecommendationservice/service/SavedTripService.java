package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.dto.SavedTripRequest;
import edu.hcmuaf.tourrecommendationservice.dto.SavedTripResponse;
import edu.hcmuaf.tourrecommendationservice.entity.SavedTrip;
import edu.hcmuaf.tourrecommendationservice.entity.User;
import edu.hcmuaf.tourrecommendationservice.repository.SavedTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavedTripService {

    @Autowired
    private SavedTripRepository savedTripRepository;


    public boolean saveTrip(SavedTripRequest savedTripRequest) throws SQLException {
        SavedTrip savedTrip = new SavedTrip();
        User user = new User();
        user.setUserId(savedTripRequest.getUserId());
        savedTrip.setSavedTripId(savedTripRequest.getSavedTripId());
        savedTrip.setSavedTripLocations(savedTripRequest.getSavedTripLocations());
        savedTrip.setUser(user);
        return savedTripRepository.saveTrip(savedTrip);
    }

    public boolean deleteSavedTrip(long savedTripId) throws SQLException {
        return savedTripRepository.deleteSavedTrip(savedTripId);
    }

    public List<SavedTripResponse> getSavedTrips(long userId) throws SQLException {
        List<SavedTrip> savedTrips = savedTripRepository.getSavedTrips(userId);
        List<SavedTripResponse> savedTripResponses = new ArrayList<>();
        for (SavedTrip savedTrip: savedTrips){
            savedTripResponses.add(new SavedTripResponse(savedTrip));
        }
        return savedTripResponses;
    }

    public SavedTripResponse getSavedTrip(long savedTripId) throws SQLException {
        return new SavedTripResponse(savedTripRepository.getSavedTrip(savedTripId));
    }

    public boolean deleteLocationFromSavedTrip(long savedTripId, long locationId) throws SQLException {
        return savedTripRepository.deleteLocationFromSavedTrip(savedTripId,locationId);
    }
}
