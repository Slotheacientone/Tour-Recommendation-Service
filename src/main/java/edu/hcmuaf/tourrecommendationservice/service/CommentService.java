package edu.hcmuaf.tourrecommendationservice.service;

import edu.hcmuaf.tourrecommendationservice.entity.CommentEntity;
import edu.hcmuaf.tourrecommendationservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentEntity> getComments(long locationId) throws SQLException {
        return commentRepository.getComments(locationId);
    }

    public boolean registerComment(long userId, long locationId, float locationRating, String comment) throws SQLException {
        return commentRepository.registerComment(userId, locationId, locationRating, comment);
    }
}
