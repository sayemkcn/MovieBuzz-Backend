package net.toracode.moviedb.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.services.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieApiController {

    @Autowired
    private MovieService movieService;

    // returns all movies paginated sort::desc
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> latestAddedMovies(@RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        List<Movie> movieList = this.movieService.getMovieListPaginated(page, size);
        if (movieList == null || movieList.isEmpty()) {
            return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    // returns movie list by movie type {movie,tvseries..} sort::desc
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> movieByType(@PathVariable("type") String type,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        List<Movie> movieList = this.movieService.getMovieByType(type, page, size);
        if (movieList == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    // returns movie by it's primary id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> movieById(@PathVariable("id") Long id) {
        Movie movie = this.movieService.getMovie(id);
        if (movie == null)
            return new ResponseEntity<Movie>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> upcomingMovies() {
        List<Movie> movieList = this.movieService.getUpcomingMovieList();
        if (movieList == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @RequestMapping(value = "/featured", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> featuredMovies() {
        List<Movie> movieList = this.movieService.getFeaturedMovieList();
        if (movieList == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    // returns movie list by its genere paginated sort::desc
    @RequestMapping(value = "/genere/{genere}", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> moviesByGenerePaginated(@PathVariable("genere") String genere,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size) {
        List<Movie> movieList = this.movieService.getMovieByGenere(genere, page, size);
        if (movieList == null || movieList.isEmpty())
            return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    // returns movie list by industry (ex. hollywood,bollywood..) paginated sort:desc
    @RequestMapping(value = "/industry/{industry}", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> moviesByIndustryPaginated(@PathVariable("industry") String industry,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        List<Movie> movieList = this.movieService.getMovieByIndustry(industry, page, size);
        if (movieList == null || movieList.isEmpty()) {
            return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    // search movie with a phrase
    @RequestMapping(value = "/search/{phrase}", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> searchMovie(@PathVariable("phrase") String phrase) {
        List<Movie> movieList = this.movieService.getMoviBySearchPhrase(phrase);
        if (movieList == null || movieList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(movieList, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public byte[] getImage(@PathVariable("id") Long id) {
        return this.movieService.getMovie(id).getImage();
    }

}
