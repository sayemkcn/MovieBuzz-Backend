package net.toracode.moviedb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.toracode.moviedb.Commons.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.entities.Review;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.PersonService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/admin/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private PersonService personService;
    @Autowired
    private ImageValidator imageValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allMovies(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "size", required = false) Integer size,
                            Model model) {
        if (page == null || size == null) {
            page = 0;
            size = 10;
        }
        model.addAttribute("movieList", this.movieService.getMovieListPaginated(page, size));
        model.addAttribute("page", page);
        return "movie/all";
    }

    // returns movie list by movie type {movie,tvseries..} sort::desc
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String movieByType(@PathVariable("type") String type,
                              Model model,
                              @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        List<Movie> movieList = this.movieService.getMovieByType(type, page, 10);
        if (movieList == null)
            return "redirect:/admin/movie?message=" + "No items found!";
        model.addAttribute("movieList", movieList);
        model.addAttribute("page", page);
        return "movie/all";
    }

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public String runningMovies(Model model,
                                @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        List<Movie> movieList = this.movieService.getUpcomingMovieList();
        if (movieList==null)
            return "redirect:/admin/movie?message="+"No Items found!";
        model.addAttribute("movieList", movieList);
        model.addAttribute("page",page);
        return "movie/all";
    }

    @RequestMapping(value = "/featured", method = RequestMethod.GET)
    public String featuredMovies(Model model,
                                 @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        List<Movie> movieList = this.movieService.getFeaturedMovieListPaginated(page, 10);
        model.addAttribute("movieList", movieList);
        model.addAttribute("page", page);
        return "movie/all";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String movieDetails(@PathVariable("id") Long id, Model model,
                               @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        Movie movie = this.movieService.getMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("personList", this.personService.getAllPersonsPaginated(page, 10));
        model.addAttribute("page", page);
        return "movie/view";
    }

    // Inject a cast or crew to a movie
    @RequestMapping(value = "/{movieId}/inject/{personId}")
    public String injectPerson(@PathVariable("movieId") Long movieId,
                               @PathVariable("personId") Long personId) {
        Movie movie = this.movieService.getMovie(movieId);
        if (movie == null)
            return "redirect:/admin/movie?message=Movie not found!";
        Person person = this.personService.getPersonById(personId);
        if (person == null)
            return "redirect:/admin/movie/" + movieId + "?message=Cast or Crew not found!";
        List<Person> personList = movie.getCastAndCrewList();
        if (personList != null) {
            // if cast already belongs to that movie
            if (this.personService.isAlreadyExists(personList, personId))
                return "redirect:/admin/movie/" + movieId + "?message=Cast or Crew already exists!";
            personList.add(person);
        } else {
            personList = new ArrayList<>();
            personList.add(person);
        }
        movie.setCastAndCrewList(personList);
        this.movieService.save(movie);
        return "redirect:/admin/movie/" + movieId;
    }

    // remove a cast or crew from movie
    @RequestMapping(value = "/{movieId}/remove/{personId}")
    public String removePerson(@PathVariable("movieId") Long movieId,
                               @PathVariable("personId") Long personId) {
        Movie movie = this.movieService.getMovie(movieId);
        if (movie == null)
            return "redirect:/admin/movie?message=Movie not found!";
        List<Person> personList = movie.getCastAndCrewList();
        if (personList == null)
            return "redirect:/admin/movie/" + movieId + "?messsage=Cast and crew list is already empty!";
        personList = this.personService.removePerson(personList, personId);
        movie.setCastAndCrewList(personList);
        this.movieService.save(movie);
        return "redirect:/admin/movie/" + movieId;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String addMoviePage(Model model) {
        model.addAttribute("personList", this.personService.findAll());
        return "movie/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addMovie(@ModelAttribute("movie") Movie movie, BindingResult bindingResult,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors())
            System.out.print(bindingResult.toString());
        if (this.imageValidator.isImageValid(multipartFile)) {
            movie.setImage(multipartFile.getBytes());
        }
        movie = this.movieService.save(movie);
        System.out.println(movie.toString());
        return "redirect:/admin/movie?message=Successfully created movie!";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateMoviePage(@PathVariable("id") Long id, Model model) {
        Movie movie = this.movieService.getMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("personList", this.personService.findAll());
        return "movie/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateMovie(@ModelAttribute("movie") Movie movie, BindingResult bindingResult,
                              @PathVariable("id") Long id,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors())
            System.out.print(bindingResult.toString());
        movie.setUniqueId(id);
        if (this.imageValidator.isImageValid(multipartFile)) {
            movie.setImage(multipartFile.getBytes());
        }
        this.movieService.save(movie);
        return "redirect:/admin/movie?message=Successfully updated " + movie.getName();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteMovie(@PathVariable("id") Long id) {
        this.movieService.deleteMovie(id);
        return "redirect:/admin/movie?message=" + "Deleted!";
    }

}
