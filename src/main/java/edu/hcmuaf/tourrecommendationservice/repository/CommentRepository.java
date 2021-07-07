package edu.hcmuaf.tourrecommendationservice.repository;

import edu.hcmuaf.tourrecommendationservice.dao.CommentDao;
import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private CommentDao commentDao;

    public List<CommentEntity> getComments(long locationId) throws SQLException {
        return commentDao.selectAllComment(locationId);
    }

    public boolean registerComment(long userId, long locationId, float locationRating, String comment) throws SQLException {
        return commentDao.insertComment(userId, locationId, locationRating, comment);
    }
}
