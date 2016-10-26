package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.ReviewService;
import net.toracode.moviedb.services.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sayemkcn on 10/25/16.
 */
@RestController
@RequestMapping(value = "/api/review")
public class ReviewApiController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Review> createReview(@ModelAttribute Review review, BindingResult bindingResult, @RequestParam("accountId") String accountId, @RequestParam("movieId") Long movieId) {
        User user = this.userService.getUserByAccountId(accountId);
        Movie movie = this.movieService.getMovie(movieId);
        // check if this fucking user has already a review in this movie
        // if true then restrict the fuck him from submitting another fucking review
        List<Review> reviewList = this.reviewService.getReviewListByMovie(movie);
        if (reviewList != null) {
            for (Review r : reviewList) {
                if (r.getUser().getAccountId().equals(accountId))
                    return new ResponseEntity<Review>(HttpStatus.LOCKED);
            }
        }

        // end
        if (user == null || movie == null)
            return new ResponseEntity<Review>(HttpStatus.NOT_ACCEPTABLE);
        review.setUser(user);
        review.setMovie(movie);
        review = this.reviewService.saveReview(review);
        return new ResponseEntity<Review>(review, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Review> updateReview(@ModelAttribute Review review, BindingResult bindingResult, @PathVariable("reviewId") Long reviewId, @RequestParam("accountId") String accountId, @RequestParam("movieId") Long movieId) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<Review>(HttpStatus.BAD_REQUEST);
        review.setUniqueId(reviewId);
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null) {
            return new ResponseEntity<Review>(HttpStatus.FORBIDDEN);
        }
        review.setUser(user);
        Movie movie = this.movieService.getMovie(movieId);
        if (movie == null) {
            return new ResponseEntity<Review>(HttpStatus.NOT_ACCEPTABLE);
        }
        review.setMovie(movie);
        review = this.reviewService.saveReview(review);
        return new ResponseEntity<Review>(review, HttpStatus.CREATED);
    }

    // MAKE PAGINATED
    @RequestMapping(value = "/user/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> reviewListByUserPaginated(
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        User user = this.userService.getUserByAccountId(accountId);
        List<Review> reviewList = this.reviewService.getReviewListByUserPaginated(user,page,size);
        return new ResponseEntity<List<Review>>(reviewList, HttpStatus.OK);
    }

}
