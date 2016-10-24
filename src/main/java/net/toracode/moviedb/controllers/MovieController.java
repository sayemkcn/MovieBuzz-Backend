package net.toracode.moviedb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.PersonService;

@Controller
@RequestMapping(value = "/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String addMoviePage(Model model) {
		model.addAttribute("personList",personService.findAll());
		return "movie/create";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String addMovie(@ModelAttribute("movie") Movie movie,BindingResult bindingResult,@RequestParam("personIds") Long[] personIds){
		List<Person> personList = personService.personListByIds(personIds);
		movie.setCastAndCrewList(personList);
		movie = movieService.save(movie);
		System.out.println(movie.toString());
		return "redirect:/movie/create?message=Successfully created movie!";
	}
}
