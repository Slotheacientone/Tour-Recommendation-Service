package edu.hcmuaf.tourrecommendationservice.recommender;

import edu.hcmuaf.tourrecommendationservice.entity.LocationEntity;
import edu.hcmuaf.tourrecommendationservice.repository.LocationRepository;
import edu.hcmuaf.tourrecommendationservice.repository.WishlistRepository;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.util.Collection;
import java.util.List;


public class TourRecommender implements Recommender {
    private DataModel model;
    private Recommender delegate;
    private WishlistRepository wishlistRepository;
    private LocationRepository locationRepository;

    public TourRecommender(DataModel model, WishlistRepository wishlistRepository, LocationRepository locationRepository) throws TasteException {
        this.wishlistRepository = wishlistRepository;
        this.model = model;
        this.locationRepository = locationRepository;
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        delegate = new GenericItemBasedRecommender(model, similarity);
    }


    @SneakyThrows
    @Override
    public List<RecommendedItem> recommend(long userId, int howMany) {
        List<LocationEntity> locations = wishlistRepository.getWishlist(userId);
        IDRescorer rescorer = new TourRescore(locations, locationRepository);
        return delegate.recommend(userId, howMany, rescorer);
    }

    @SneakyThrows
    @Override
    public List<RecommendedItem> recommend(long userId, int howMany, boolean b) {
        List<LocationEntity> locations = wishlistRepository.getWishlist(userId);
        IDRescorer rescorer = new TourRescore(locations, locationRepository);
        return delegate.recommend(userId, howMany, rescorer);
    }

    @Override
    public List<RecommendedItem> recommend(long userId, int howMany, IDRescorer rescorer) throws TasteException {
        return delegate.recommend(userId, howMany, rescorer);
    }

    @Override
    public List<RecommendedItem> recommend(long userId, int howMany, IDRescorer rescorer, boolean b) throws TasteException {
        return delegate.recommend(userId, howMany, rescorer);
    }

    @SneakyThrows
    @Override
    public float estimatePreference(long userId, long itemId) {
        List<LocationEntity> locations = wishlistRepository.getWishlist(userId);
        IDRescorer rescorer = new TourRescore(locations, locationRepository);
        return (float) rescorer.rescore(itemId, delegate.estimatePreference(userId, itemId));
    }

    @Override
    public void setPreference(long userId, long itemId, float value) throws TasteException {
        delegate.setPreference(userId, itemId, value);
    }

    @Override
    public void removePreference(long userId, long itemId) throws TasteException {
        delegate.removePreference(userId, itemId);
    }

    @Override
    public DataModel getDataModel() {
        return delegate.getDataModel();
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        delegate.refresh(alreadyRefreshed);
    }
}
