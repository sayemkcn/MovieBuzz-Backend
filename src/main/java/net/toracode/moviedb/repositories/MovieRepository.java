package net.toracode.moviedb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.toracode.moviedb.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	public Page<Movie> findByGenere(String genere,Pageable pagable);
	public Page<Movie> findByIndustry(String genere,Pageable pagable);
}
