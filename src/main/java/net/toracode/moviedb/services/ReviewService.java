package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sayemkcn on 10/25/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Review saveReview(Review review) {
        return this.reviewRepo.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByMovie(Movie movie) {
        return this.reviewRepo.findByMovie(movie);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByMovie(Movie movie, int page, int size) {
        return this.reviewRepo.findByMovie(movie, new PageRequest(page, size, Sort.Direction.DESC, "uniqueId"));
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByUserPaginated(User user, int page, int size) {
        return this.reviewRepo.findByUser(user, new PageRequest(page, size, Sort.Direction.DESC, "uniqueId"));
    }

    public Float calculateAverageRating(List<Review> reviewList) {
        float sum = 0f;
        for (Review review : reviewList) {
            sum += review.getRating();
        }
        return sum / reviewList.size();
    }
}
