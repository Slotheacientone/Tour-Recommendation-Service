package edu.hcmuaf.tourrecommendationservice.service;


import edu.hcmuaf.tourrecommendationservice.dto.LocationResponse;
import edu.hcmuaf.tourrecommendationservice.dto.RecommendResponse;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
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

    public long calculateDistance(double currentLatitude, double currentLongtitude, LocationResponse locationResponse) throws IOException {
        int distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
        String destination;
        if (locationResponse.getLocationLatitude() != 0.0 && locationResponse.getLocationLongitude() != 0.0) {
            destination = locationResponse.getLocationLatitude() + "," + locationResponse.getLocationLongitude();
        } else {
            destination = locationResponse.getLocationName();
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
        if (response.isSuccessful()) {
            DistanceApiResult result = Utils.fromJson(response.body().string(), DistanceApiResult.class);
            try {
                distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return Math.round(distance/1000.0);
    }


    public void calculateDistances(double currentLatitude, double currentLongtitude, List<LocationResponse> locationResponses) throws IOException {
        boolean iscalculateDistanceSuccessfull = true;
        for (LocationResponse locationResponse : locationResponses) {
            long distance;
            if (locationResponse.getLocationLatitude() == 0 || locationResponse.getLocationLongitude() == 0) {
                try {
                    geocodingApiService.getLatlong(locationResponse);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            distance = calculateDistance(currentLatitude, currentLongtitude, locationResponse);
            if (distance == 0 || distance == -1) {
                iscalculateDistanceSuccessfull = false;
                break;
            }
            locationResponse.setDistance(distance);
        }
        if (!iscalculateDistanceSuccessfull) {
            long distance;
            for (LocationResponse locationResponse : locationResponses) {
                if (locationResponse.getLocationLatitude() == 0 || locationResponse.getLocationLongitude() == 0) {
                    try {
                        geocodingApiService.getLatlong(locationResponse);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                distance =  haversineDistance(currentLatitude, currentLongtitude, locationResponse.getLocationLatitude(), locationResponse.getLocationLongitude());
                locationResponse.setDistance(distance);
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
