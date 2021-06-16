package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.InterfaceAPI.DistanceApiInterface;
import edu.hcmuaf.tourrecommendationservice.model.googleMapDistance.DistanceApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class GoogleMapService {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/";
    private static final String API_KEY = "<Google Api Key>";
    private DistanceApiInterface distanceApi;
    private int distance;

    @Autowired
    public GoogleMapService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        distanceApi = retrofit.create(DistanceApiInterface.class);
    }

    public int calculateDistance(double currentLatitude, double currentLongtitude, double destinationLatitude, double destinationLongtitude) {
        distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
        String destination = destinationLatitude + "," + destinationLongtitude;
        Call<DistanceApiResult> call = distanceApi.getDistanceBetweenTwoLocation("Metric", origin, destination, API_KEY);
        call.enqueue(new Callback<DistanceApiResult>() {
            @Override
            public void onResponse(Call<DistanceApiResult> call, Response<DistanceApiResult> response) {
                distance = response.body().getRows().get(0).getElements().get(0).getDistance().getValue();
            }

            @Override
            public void onFailure(Call<DistanceApiResult> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
        return distance;
    }

    public int calculateDistance(double currentLatitude, double currentLongtitude, String destination) {
        distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
        Call<DistanceApiResult> call = distanceApi.getDistanceBetweenTwoLocation("Metric", origin, destination, API_KEY);
        call.enqueue(new Callback<DistanceApiResult>() {
            @Override
            public void onResponse(Call<DistanceApiResult> call, Response<DistanceApiResult> response) {
                distance = response.body().getRows().get(0).getElements().get(0).getDistance().getValue();
            }

            @Override
            public void onFailure(Call<DistanceApiResult> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
        return distance;
    }
}
