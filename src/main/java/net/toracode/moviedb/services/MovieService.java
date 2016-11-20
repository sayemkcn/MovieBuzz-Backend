package net.toracode.moviedb.services;

import java.util.List;

import net.toracode.moviedb.entities.CustomList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.repositories.MovieRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MovieService {
    private static final String FIELD_NAME = "uniqueId";
    @Autowired
    private MovieRepository movieRepo;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    @Transactional(readOnly = true)
    public Movie getMovie(Long id) {
        return this.movieRepo.findOne(id);
    }

    @Transactional(readOnly = false)
    public void deleteMovie(Long id) {
        this.movieRepo.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Movie> getMovieListPaginated(int pageNumber, int size) {
        Page<Movie> page = this.movieRepo.findAll(new PageRequest(pageNumber, size, Sort.Direction.DESC, FIELD_NAME));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Movie> getMovieByGenere(String genere, int pageNumber, int size) {
        Page<Movie> page = this.movieRepo.findByGenere(genere, new PageRequest(pageNumber, size, Sort.Direction.DESC, FIELD_NAME));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Movie> getMovieByIndustry(String industry, int pageNumber, int size) {
        Page<Movie> page = this.movieRepo.findByIndustry(industry, new PageRequest(pageNumber, size, Sort.Direction.DESC, FIELD_NAME));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Movie> getMovieByType(String type, int pageNumber, int size) {
        Page<Movie> page = this.movieRepo.findByType(type, new PageRequest(pageNumber, size, Sort.Direction.DESC, FIELD_NAME));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviBySearchPhrase(String phrase) {
        return this.movieRepo.findByNameContaining(phrase);
    }

    @Transactional(readOnly = true)
    public List<Movie> getUpcomingMovieList() {
        return this.movieRepo.findByUpcomingTrue();
    }

    @Transactional(readOnly = true)
    public List<Movie> getFeaturedMovieList() {
        return this.movieRepo.findByFeaturedTrue();
    }

    @Transactional(readOnly = true)
    public List<Movie> getFeaturedMovieListPaginated(int page, int size) {
        return this.movieRepo.findByFeaturedTrue(new PageRequest(page, size, Sort.Direction.DESC, FIELD_NAME)).getContent();
    }

    public boolean isListAlreadyAssociatedWithThisMovie(Movie movie, CustomList customList) {
        for (CustomList list : movie.getListOfCustomList()) {
            if (list.getUniqueId().equals(customList.getUniqueId()))
                return true;
        }
        return false;
    }
}
