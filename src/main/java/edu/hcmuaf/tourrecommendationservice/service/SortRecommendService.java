package edu.hcmuaf.tourrecommendationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SortRecommendService {

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;

//    public List<LocationEntity> sortByDistance(double currentLatitude, double currentLongtitude, List<LocationEntity> recommendEntities) {
//        Collections.sort(recommendEntities, new Comparator<LocationEntity>() {
//            @SneakyThrows
//            @Override
//            public int compare(LocationEntity locationEntity1, LocationEntity locationEntity2) {
//                int distance1 = 0;
//                int distance2 = 0;
//                if(locationEntity1.getLocationLatitude()==0.0&&locationEntity1.getLocationLongtitude()==0.0){
//                    distance1 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,locationEntity1.getLocationLatitude(),locationEntity1.getLocationLongtitude());
//                }else{
//                    distance1 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,locationEntity1.getLocationName());
//                }
//                if(locationEntity2.getLocationLatitude()==0.0&&locationEntity2.getLocationLongtitude()==0.0){
//                    distance2 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,locationEntity2.getLocationLatitude(),locationEntity2.getLocationLongtitude());
//                }else{
//                    distance2 = googleMapService.calculateDistance(currentLatitude,currentLongtitude,locationEntity2.getLocationName());
//                }
//                locationEntity1.setDistance(distance1);
//                locationEntity2.setDistance(distance2);
//                return distance1 < distance2 ? -1 : 1;
//            }
//        });
//        return recommendEntities;
//    }
}
