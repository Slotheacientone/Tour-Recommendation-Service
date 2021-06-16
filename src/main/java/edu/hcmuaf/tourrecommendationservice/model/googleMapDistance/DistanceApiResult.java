package edu.hcmuaf.tourrecommendationservice.model.googleMapDistance;

import com.google.gson.annotations.SerializedName;
import edu.hcmuaf.tourrecommendationservice.model.googleMapDistance.Row;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DistanceApiResult {
    @SerializedName("destination_addresses")
    private List<String> destination;

    @SerializedName("origin_addresses")
    private List<String> origin;

    private List<Row> rows;

    private String status;
}
