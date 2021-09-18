package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.database.DatabaseManager;
import edu.hcmuaf.tourrecommendationservice.dto.LocationResponse;
import edu.hcmuaf.tourrecommendationservice.dto.RecommendResponse;
import edu.hcmuaf.tourrecommendationservice.dto.SimilarLocationResponse;
import edu.hcmuaf.tourrecommendationservice.entity.Location;
import edu.hcmuaf.tourrecommendationservice.recommender.TourRecommender;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import edu.hcmuaf.tourrecommendationservice.repository.WishlistRepository;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
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
     * Location service.
     */
    @Autowired
    private LocationService locationService;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private LocationRepository locationRepository;

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
     * @return List of {@link Location}
     * @throws TasteException Taste exception
     */
    public List<LocationResponse> recommend(long userId, int numberOfRecommendation) throws TasteException, SQLException {
        List<LocationResponse> result = new ArrayList<>();

        JDBCDataModel dataModel1 = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);

        Recommender recommender = new TourRecommender(dataModel1, wishlistRepository, locationRepository);

        List<RecommendedItem> list = recommender.recommend(userId, numberOfRecommendation);
        int order =1;
        for (RecommendedItem item : list) {
            long locationId = item.getItemID();
            Location location = locationService.getLocation(locationId);
            RecommendResponse recommendResponse = new RecommendResponse(location);
            recommendResponse.setOrder(order);
            order++;
            result.add(recommendResponse);
        }
        return result;
    }

    /**
     * Recommend location for user.
     *
     * @param userId user id
     * @return List of {@link Location}
     * @throws TasteException Taste exception
     */
    public List<LocationResponse> recommend(long userId, int numberOfRecommendation, double latitude, double longitude) throws TasteException, SQLException, IOException {
        List<LocationResponse> result = new ArrayList<>();
        JDBCDataModel dataModel = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);

        Recommender recommender = new TourRecommender(dataModel, wishlistRepository, locationRepository);
        List<RecommendedItem> list = recommender.recommend(userId, numberOfRecommendation);
        int order = 1;
        for (RecommendedItem item : list) {
            long locationId = item.getItemID();
            Location location = locationService.getLocation(locationId);
            RecommendResponse recommendResponse = new RecommendResponse(location);
            recommendResponse.setOrder(order);
            order++;
            result.add(recommendResponse);
        }
        distanceMatrixApiService.calculateDistances(latitude,longitude, result);
        return result;
    }

    public List<LocationResponse> getMostSimilarItems(long locationId) throws TasteException, SQLException {
        List<LocationResponse> result = new ArrayList<>();
        JDBCDataModel dataModel = new MySQLJDBCDataModel(databaseManager.getDataSource(), "user_rating", "user_id", "location_id", "preference", null);
        ItemSimilarity itemSimilarity = new UncenteredCosineSimilarity(dataModel);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        List<RecommendedItem> list = recommender.mostSimilarItems(locationId,5);
        for (RecommendedItem item : list) {
            long id = item.getItemID();
            Location location = locationService.getLocation(id);
            SimilarLocationResponse similarLocationResponse = new SimilarLocationResponse(location);
            result.add(similarLocationResponse);
        }
        return result;
    }

}
