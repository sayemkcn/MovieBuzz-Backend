package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.ReviewService;
import net.toracode.moviedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        if (user == null || movie == null)
            return new ResponseEntity<Review>(HttpStatus.NOT_ACCEPTABLE);
        review.setUser(user);
        review.setMovie(movie);
        review = this.reviewService.saveReview(review);
        return new ResponseEntity<Review>(review,HttpStatus.CREATED);
    }

}
