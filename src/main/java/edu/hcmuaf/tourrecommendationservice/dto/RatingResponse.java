package edu.hcmuaf.tourrecommendationservice.dto;

import edu.hcmuaf.tourrecommendationservice.entity.Rating;
import lombok.Data;

import java.util.Date;

@Data
public class RatingResponse {
    /** User id. */
    private long userId;

    private String userName;

    private String userImageUrl;

    /** User rating. */
    private float rating;

    private String comment;

    private Date date;

    public RatingResponse(Rating rating){
        this.userId = rating.getUser().getUserId();
        this.userName = rating.getUser().getUsername();
        this.userImageUrl = rating.getUser().getThumbnail();
        this.rating = rating.getPreference();
        this.comment = rating.getComment();
        this.date = rating.getDate();
    }
}
