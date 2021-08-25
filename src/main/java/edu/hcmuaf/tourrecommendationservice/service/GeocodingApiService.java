package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.util.ApiClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class GeocodingApiService {

    @Value("${geocoding.api.base.uri}")
    private String geocodingApiBaseUri;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    private LocationService locationService;

    public boolean getLatlong(LocationEntity location) throws IOException, SQLException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(geocodingApiBaseUri).newBuilder();
        urlBuilder.addQueryParameter("address", location.getLocationName());
        urlBuilder.addQueryParameter("key", googleApiKey);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        System.out.println(request);
        Response response = ApiClient.getClient().newCall(request).execute();
        if (response!=null && response.isSuccessful()) {
          String json = response.body().string();
            JSONObject geocoderResult = new JSONObject(json);
            JSONArray results = geocoderResult.getJSONArray("results");
            JSONObject result = results.getJSONObject(0);
            JSONObject geometry = result.getJSONObject("geometry");
            JSONObject latlong = geometry.getJSONObject("location");
            location.setLocationLatitude(latlong.getDouble("lat"));
            location.setLocationLongitude(latlong.getDouble("lng"));
            locationService.setLatLong(location);
            return true;
        }
        return false;
    }

}
