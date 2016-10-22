package net.toracode.moviedb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.services.MovieService;

@Controller
@RequestMapping(value = "/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String addMoviePage() {
		Movie movie = new Movie();
		movie.setName("Inception");
		movie.setIndustry("HollyWood");
		movie.setDuration("3h 10m");
		Person cast = new Person();
		cast.setName("Leo DiCaprio");
		cast.setBio("Very good actor");
		cast.setDesignations(new String[] { "Actor", "Producer" });
		List<Person> castList = new ArrayList<>();
		castList.add(cast);
		movie.setCastAndCrewList(castList);
		Review review = new Review();
		review.setTitle("Review Title");
		review.setMessage("Message of the review");
		review.setRating(9);
		List<Review> reviewList = new ArrayList<>();
		reviewList.add(review);
		movie.setReviewList(reviewList);
		movieService.save(movie);
		return "movie/create";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String addMovie(@ModelAttribute("movie") Movie movie,BindingResult bindingResult){
		System.out.println(movie.toString());
		return "movie/create";
	}
}
