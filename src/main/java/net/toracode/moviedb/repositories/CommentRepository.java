package net.toracode.moviedb.repositories;

import net.toracode.moviedb.entities.Comment;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sayemkcn on 11/15/16.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMovie(Movie movie, Pageable pageable);

    List<Comment> findByUser(User user, Pageable pageable);
}
