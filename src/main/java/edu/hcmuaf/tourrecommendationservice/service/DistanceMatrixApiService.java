package edu.hcmuaf.tourrecommendationservice.service;


import edu.hcmuaf.tourrecommendationservice.InterfaceAPI.DistanceApiInterface;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.model.googleMapDistance.DistanceApiResult;
import edu.hcmuaf.tourrecommendationservice.util.ApiClient;
import edu.hcmuaf.tourrecommendationservice.util.Utils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DistanceMatrixApiService {

    @Value("${distance.matrix.api.base.uri}")
    private String distanceMatrixApiBaseUri;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    private GeocodingApiService geocodingApiService;

    public long calculateDistance(double currentLatitude, double currentLongtitude, LocationEntity locationEntity) throws ExecutionException, InterruptedException, IOException {
        System.out.println("calculate");
        int distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
        String destination;
        if (locationEntity.getLocationLatitude() != 0.0 && locationEntity.getLocationLongitude() != 0.0) {
            destination = locationEntity.getLocationLatitude() + "," + locationEntity.getLocationLongitude();
        } else {
            destination = locationEntity.getLocationName();
        }
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(distanceMatrixApiBaseUri).newBuilder();
        urlBuilder.addQueryParameter("units", "Metric");
        urlBuilder.addQueryParameter("origins", origin);
        urlBuilder.addQueryParameter("destinations", destination);
        urlBuilder.addQueryParameter("key", googleApiKey);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        System.out.println(request);
        Response response = ApiClient.getClient().newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            DistanceApiResult result = Utils.fromJson(response.body().string(), DistanceApiResult.class);
            distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
        }
        return Math.round(distance/1000.0);
    }


    public void calculateDistances(double currentLatitude, double currentLongtitude, List<LocationEntity> locations) throws IOException, ExecutionException, InterruptedException, SQLException {
        boolean iscalculateDistanceSuccessfull = true;
        for (LocationEntity locationEntity : locations) {
            long distance = -1;
            if (locationEntity.getLocationLatitude() == 0 || locationEntity.getLocationLongitude() == 0) {
                geocodingApiService.getLatlong(locationEntity);
            }
            distance = calculateDistance(currentLatitude, currentLongtitude, locationEntity);
            if (distance == 0 || distance == -1) {
                iscalculateDistanceSuccessfull = false;
                break;
            }
            locationEntity.setDistance(distance);
        }
        if (!iscalculateDistanceSuccessfull) {
            long distance = -1;
            for (LocationEntity locationEntity : locations) {
                if (locationEntity.getLocationLatitude() == 0 || locationEntity.getLocationLongitude() == 0) {
                    geocodingApiService.getLatlong(locationEntity);
                }
                distance =  haversineDistance(currentLatitude, currentLongtitude, locationEntity.getLocationLatitude(), locationEntity.getLocationLongitude());
                locationEntity.setDistance(distance);
            }
        }
    }

    public long haversineDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double distanceBetweenLatitudes = Math.toRadians(latitude2 - latitude1);
        double distanceBetweenLongitude = Math.toRadians(longitude2 - longitude1);

        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);

        double a = Math.pow(Math.sin(distanceBetweenLatitudes / 2), 2) +
                Math.pow(Math.sin(distanceBetweenLongitude / 2), 2) *
                        Math.cos(latitude1) *
                        Math.cos(latitude2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return Math.round(rad * c);
    }
}
