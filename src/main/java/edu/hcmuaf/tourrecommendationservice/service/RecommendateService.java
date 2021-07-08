package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
import edu.hcmuaf.tourrecommendationservice.service.LocationService;
import edu.hcmuaf.tourrecommendationservice.service.UserService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Recommendator.
 *
 * @author Viet-PH
 */
@Component
public class RecommendateService {

    /** User service. */
    @Autowired
    private UserService userService;

    /** Location service. */
    @Autowired
    private LocationService locationService;

    /** Database manager. */
    @Autowired
    private DatabaseManager databaseManager;

    /**
     * Recommend location for user.
     *
     * @param userId user id
     * @return List of {@link RecommendEntity}
     * @throws TasteException Taste exception
     */
    public List<LocationEntity> recommend(long userId, int numberOfRecommendation) throws TasteException, SQLException {
        List<LocationEntity> result = new ArrayList<>();
        // Item base PearsonCorrelation
        JDBCDataModel dataModel1 = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);
        ItemSimilarity itemSimilarity1 = new PearsonCorrelationSimilarity(dataModel1);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel1, itemSimilarity1);
        List<RecommendedItem> list = recommender.recommend(userId, 3);
        for(RecommendedItem item: list){
            long locationId = item.getItemID();
            LocationEntity locationEntity = locationService.getLocation(locationId);
            result.add(locationEntity);
        }
        return result;
    }


}
