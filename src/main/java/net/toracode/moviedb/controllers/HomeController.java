package net.toracode.moviedb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.repositories.MovieRepository;

@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	private MovieRepository repo;
	
//	@RequestMapping(value="",method=RequestMethod.GET)
//	public ResponseEntity<Movie> sayHello(){
//		Movie movie = new Movie();
//		movie.setName("Baler Movie");
//		movie.add(new Link("/api"));
//		
//		return new ResponseEntity<>(repo.saveAndFlush(movie),HttpStatus.OK) ;
//	}
	
}
