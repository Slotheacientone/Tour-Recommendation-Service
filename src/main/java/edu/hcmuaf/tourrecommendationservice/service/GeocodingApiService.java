package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
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
public class GeocodingApiService {

    @Value("${geocoding.api.base.uri}")
    private String geocodingApiBaseUri;

    @Value("${google.api.key}")
    private String googleApiKey;

    public void setLatlong(LocationEntity location) throws ExecutionException, InterruptedException, IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(geocodingApiBaseUri).newBuilder();
        urlBuilder.addQueryParameter("address", location.getLocationName());
        urlBuilder.addQueryParameter("key", googleApiKey);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = ApiClient.sendAsync(request).get();
        if (response!=null && response.isSuccessful()) {
          String json = response.body().string();

        }
    }

}
