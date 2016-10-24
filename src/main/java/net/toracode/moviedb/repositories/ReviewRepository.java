package net.toracode.moviedb.repositories;

import net.toracode.moviedb.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sayemkcn on 10/25/16.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
