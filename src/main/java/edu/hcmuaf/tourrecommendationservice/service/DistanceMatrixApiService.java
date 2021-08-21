package edu.hcmuaf.tourrecommendationservice.service;


import edu.hcmuaf.tourrecommendationservice.InterfaceAPI.DistanceApiInterface;
import edu.hcmuaf.tourrecommendationservice.model.googleMapDistance.DistanceApiResult;
import edu.hcmuaf.tourrecommendationservice.util.ApiClient;
import edu.hcmuaf.tourrecommendationservice.util.Utils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class DistanceMatrixApiService {

    @Value("${distance.matrix.api.base.uri}")
    private String distanceMatrixApiBaseUri;

    @Value("${google.api.key}")
    private String googleApiKey;

    public int calculateDistance(double currentLatitude, double currentLongtitude, double destinationLatitude, double destinationLongtitude) throws ExecutionException, InterruptedException, IOException {
        int distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
        String destination = destinationLatitude + "," + destinationLongtitude;
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
        Response response = ApiClient.sendAsync(request).get();
        if (response!=null && response.isSuccessful()) {
            DistanceApiResult result = Utils.fromJson(response.body().string(), DistanceApiResult.class);
            distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
        }
        return distance;
    }

    public int calculateDistance(double currentLatitude, double currentLongtitude, String destination) throws IOException, ExecutionException, InterruptedException {
        int distance = -1;
        String origin = currentLatitude + "," + currentLongtitude;
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
        Response response = ApiClient.sendAsync(request).get();
        if (response!=null && response.isSuccessful()) {
            DistanceApiResult result = Utils.fromJson(response.body().string(), DistanceApiResult.class);
            distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
        }
        return distance;
    }
}
