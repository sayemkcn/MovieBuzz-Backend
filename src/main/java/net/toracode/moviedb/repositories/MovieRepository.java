package net.toracode.moviedb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.toracode.moviedb.entities.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByGenere(String genere, Pageable pagable);

    Page<Movie> findByIndustry(String genere, Pageable pagable);

    Page<Movie> findByType(String type, Pageable pageable);

    List<Movie> findByNameContaining(String name);

    List<Movie> findByUpcomingTrue();

    List<Movie> findByFeaturedTrue();
}
