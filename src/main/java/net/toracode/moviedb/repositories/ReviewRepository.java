package net.toracode.moviedb.repositories;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sayemkcn on 10/25/16.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByMovie(Movie movie);
    public List<Review> findByUser(User user, Pageable pageable);
}
