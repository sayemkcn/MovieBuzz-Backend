package net.toracode.moviedb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allMovies(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page == null || size == null) {
            page = 0;
            size = 10;
        }
        model.addAttribute("movieList", this.movieService.getLatestMovies(page, size));
        return "movie/all";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String addMoviePage(Model model) {
        model.addAttribute("personList", this.personService.findAll());
        return "movie/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addMovie(@ModelAttribute("movie") Movie movie, BindingResult bindingResult, @RequestParam("personIds") Long[] personIds) {
        if (bindingResult.hasErrors())
            System.out.print(bindingResult.toString());
        List<Person> personList = this.personService.personListByIds(personIds);
        movie.setCastAndCrewList(personList);
        movie = this.movieService.save(movie);
        System.out.println(movie.toString());
        return "redirect:/movie/create?message=Successfully created movie!";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateMoviePage(@PathVariable("id") Long id, Model model) {
        Movie movie = this.movieService.getMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("personList", this.personService.findAll());
        return "movie/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateMovie(@ModelAttribute("movie") Movie movie, BindingResult bindingResult, @PathVariable("id") Long id, @RequestParam(value = "personIds", required = false) Long[] personIds) {
        if (bindingResult.hasErrors())
            System.out.print(bindingResult.toString());
        movie.setUniqueId(id);
        List<Person> personList = this.personService.personListByIds(personIds);
        movie.setCastAndCrewList(personList);
        this.movieService.save(movie);
        return "redirect:/movie/create?message=Successfully updated " + movie.getName();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteMovie(@PathVariable("id") Long id) {
        this.movieService.deleteMovie(id);
        return "redirect:/movie";
    }

}
