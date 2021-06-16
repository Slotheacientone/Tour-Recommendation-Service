package edu.hcmuaf.tourrecommendationservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CommentEntity {
    /** User id. */
    private long userId;

    private String userName;

    private String userImageUrl;

    /** User rating. */
    private float rating;

    private String comment;
}
