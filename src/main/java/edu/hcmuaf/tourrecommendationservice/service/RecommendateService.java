package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.entity.RecommendEntity;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Recommendator.
 *
 * @author Viet-PH
 */
@Component
public class RecommendateService {

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

    /**
     * Location service.
     */
    @Autowired
    private LocationService locationService;

    /**
     * Database manager.
     */
    @Autowired
    private DatabaseManager databaseManager;

    @Autowired
    private DistanceMatrixApiService distanceMatrixApiService;

    /**
     * Recommend location for user.
     *
     * @param userId user id
     * @return List of {@link LocationEntity}
     * @throws TasteException Taste exception
     */
    public List<LocationEntity> recommend(long userId, int numberOfRecommendation) throws TasteException, SQLException {
        List<LocationEntity> result = new ArrayList<>();
        // Item base PearsonCorrelation
        JDBCDataModel dataModel1 = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);
        ItemSimilarity itemSimilarity1 = new PearsonCorrelationSimilarity(dataModel1);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel1, itemSimilarity1);
        List<RecommendedItem> list = recommender.recommend(userId, numberOfRecommendation);
        for (RecommendedItem item : list) {
            long locationId = item.getItemID();
            float recommendScore = item.getValue();
            LocationEntity locationEntity = locationService.getLocation(locationId);
            locationEntity.setRecommendScore(recommendScore);
            result.add(locationEntity);
        }
        return result;
    }

    /**
     * Recommend location for user.
     *
     * @param userId user id
     * @return List of {@link RecommendEntity}
     * @throws TasteException Taste exception
     */
    public List<LocationEntity> recommend(long userId, int numberOfRecommendation, double latitude, double longitude) throws TasteException, SQLException, IOException, ExecutionException, InterruptedException {
        List<LocationEntity> result = new ArrayList<>();
        // Item base PearsonCorrelation
        JDBCDataModel dataModel1 = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);
        ItemSimilarity itemSimilarity1 = new PearsonCorrelationSimilarity(dataModel1);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel1, itemSimilarity1);
        List<RecommendedItem> list = recommender.recommend(userId, numberOfRecommendation);
        for (RecommendedItem item : list) {
            long locationId = item.getItemID();
            float recommendScore = item.getValue();
            long distance = -1;
            LocationEntity locationEntity = locationService.getLocation(locationId);
            distance = distanceMatrixApiService.calculateDistance(latitude, longitude, locationEntity);
            locationEntity.setDistance(distance);
            locationEntity.setRecommendScore(recommendScore);
            result.add(locationEntity);
        }
        return result;
    }


}
