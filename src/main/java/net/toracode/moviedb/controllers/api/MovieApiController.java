package net.toracode.moviedb.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.services.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieApiController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> sayHello(@RequestParam("page") int start, @RequestParam("size") int size) {
		return new ResponseEntity<List<Movie>>(movieService.getMovieListPaginated(start, size), HttpStatus.OK);
	}

}
