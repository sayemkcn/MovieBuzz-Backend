package net.toracode.moviedb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.toracode.moviedb.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
//	public Page<Movie> findAll(Pageable pagable);
}
