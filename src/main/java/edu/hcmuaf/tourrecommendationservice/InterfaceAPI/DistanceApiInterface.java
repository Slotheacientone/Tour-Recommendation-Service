package edu.hcmuaf.tourrecommendationservice.InterfaceAPI;

import edu.hcmuaf.tourrecommendationservice.model.googleMapDistance.DistanceApiResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceApiInterface {

    @GET("json")
    Call<DistanceApiResult> getDistanceBetweenTwoLocation(@Query("units") String units, @Query("origins") String origins, @Query("destinations") String destinations, @Query("key") String key);
}
