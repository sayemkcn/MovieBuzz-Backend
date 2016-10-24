package net.toracode.moviedb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.repositories.MovieRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MovieService {
	@Autowired
	private MovieRepository movieRepo;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Movie save(Movie movie) {
		return movieRepo.save(movie);
	}

	@Transactional(readOnly=true)
	public List<Movie> getMovieListPaginated(int pageNumber, int size) {
		Page<Movie> page = this.movieRepo.findAll(new PageRequest(pageNumber, size));
		return page.getContent();
	}
	public List<Movie> getMovieByGenere(String genere,int pageNumber,int size){
		Page<Movie> page = this.movieRepo.findByGenere(genere,new PageRequest(pageNumber,size));
		return page.getContent();
	}
	public List<Movie> getMovieByIndustry(String industry,int pageNumber,int size){
		Page<Movie> page = this.movieRepo.findByIndustry(industry,new PageRequest(pageNumber,size));
		return page.getContent();
	}
}
