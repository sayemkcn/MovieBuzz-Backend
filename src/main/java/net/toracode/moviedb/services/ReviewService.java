package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sayemkcn on 10/25/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Review saveReview(Review review){
        return this.reviewRepo.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewByMovie(Movie movie){
        return this.reviewRepo.findByMovie(movie);
    }
}
