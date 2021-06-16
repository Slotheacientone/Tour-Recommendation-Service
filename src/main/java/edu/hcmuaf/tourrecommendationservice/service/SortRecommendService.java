package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SortRecommendService {

    @Autowired
    private GoogleMapService googleMapService;

    public List<RecommendEntity> sortByRating(List<RecommendEntity> recommendEntities) {
        Collections.sort(recommendEntities, new Comparator<RecommendEntity>() {
            @Override
            public int compare(RecommendEntity recommendEntity1, RecommendEntity recommendEntity2) {
                return recommendEntity1.getRating() > recommendEntity2.getRating() ? -1 : 1;
            }
        });
        return recommendEntities;
    }

    public List<RecommendEntity> sortByDistance(double currentLatitude, double currentLongtitude, List<RecommendEntity> recommendEntities) {
        Collections.sort(recommendEntities, new Comparator<RecommendEntity>() {
            @Override
            public int compare(RecommendEntity recommendEntity1, RecommendEntity recommendEntity2) {
                int distance1 = 0;
                int distance2 = 0;
                if(recommendEntity1.getLocationLatitude()==0.0&&recommendEntity1.getLocationLongtitude()==0.0){
                    distance1 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,recommendEntity1.getLocationLatitude(),recommendEntity1.getLocationLongtitude());
                }else{
                    distance1 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,recommendEntity1.getLocationName());
                }
                if(recommendEntity2.getLocationLatitude()==0.0&&recommendEntity2.getLocationLongtitude()==0.0){
                    distance2 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,recommendEntity2.getLocationLatitude(),recommendEntity2.getLocationLongtitude());
                }else{
                    distance2 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,recommendEntity2.getLocationName());
                }

                return distance1 < distance2 ? -1 : 1;
            }
        });
        return recommendEntities;
    }
}
