package net.toracode.moviedb.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.services.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieApiController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> allMoviesPaginated(@RequestParam("page") int start, @RequestParam("size") int size) {
		return new ResponseEntity<List<Movie>>(this.movieService.getMovieListPaginated(start, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/genere/{genere}",method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> moviesByGenerePaginated(@PathVariable("genere") String genere,@RequestParam("page") int page, @RequestParam("size") int size){
		return new ResponseEntity<List<Movie>>(this.movieService.getMovieByGenere(genere,page,size),HttpStatus.OK);
	}

	@RequestMapping(value = "/industry/{industry}",method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> moviesByIndustryPaginated(@PathVariable("industry") String industry,@RequestParam("page") int page,@RequestParam("size") int size){
		return new ResponseEntity<List<Movie>>(this.movieService.getMovieByIndustry(industry,page,size),HttpStatus.OK);
	}

	@RequestMapping(value = "/latest",method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> latestAddedMovies(@RequestParam("page") int page,@RequestParam("size") int size){
		return new ResponseEntity<List<Movie>>(this.movieService.getLatestMovies(page,size),HttpStatus.OK);
	}
}
