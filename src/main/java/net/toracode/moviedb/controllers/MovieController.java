package net.toracode.moviedb.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        if (movieList == null)
            return "redirect:/admin/movie?message=" + "No Items found!";
        model.addAttribute("movieList", movieList);
        model.addAttribute("page", page);
        return "movie/all";
    }

    // returns all of the featured movies paginated.
    @RequestMapping(value = "/featured", method = RequestMethod.GET)
    public String featuredMovies(Model model,
                                 @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 0;
        List<Movie> movieList = this.movieService.getFeaturedMovieListPaginated(page, 10);
        model.addAttribute("movieList", movieList);
        model.addAttribute("page", page);
        return "movie/all";
    }

    // movie details page // get a movie by id
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
    @RequestMapping(value = "/{movieId}/inject/{personId}", method = RequestMethod.POST)
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

    // search by person name
    @RequestMapping(value = "/{movieId}/person/search", method = RequestMethod.POST)
    public String searchCastAndCrew(@PathVariable("movieId") Long movieId,
                                    @RequestParam("phrase") String phrase,
                                    @RequestParam("page") Integer page,
                                    @RequestParam("size") Integer size, Model model) {
        if (page == null || size == null) {
            page = 0;
            size = 0;
        }
        Movie movie = this.movieService.getMovie(movieId);
        List<Person> personList = this.personService.searchPersonByNamePaginated(phrase, page, size);
        model.addAttribute("movie", movie);
        model.addAttribute("personList", personList);
        model.addAttribute("page", page);
        return "movie/view";
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
        if (movie==null)
            return "redirect:/admin/movie?message=Can not create movie!";
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
            System.out.println(bindingResult.toString());
        Movie existingMovie = this.movieService.getMovie(id);
        // set movie id
        movie.setUniqueId(id);
        // set cast and crew list from previous entity.
        movie.setCastAndCrewList(existingMovie.getCastAndCrewList());
        // set date from previous entity if null
        if (movie.getReleaseDate() == null)
            movie.setReleaseDate(existingMovie.getReleaseDate());
        // chack if image is choosen
        if (!multipartFile.isEmpty()) {
            if (this.imageValidator.isImageValid(multipartFile)) {
                movie.setImage(multipartFile.getBytes());
            }
        } else {
            // else fetch previous image if existed and set it;
            byte[] image = existingMovie.getImage();
            if (image != null)
                movie.setImage(image);
        }
        this.movieService.save(movie);
        return "redirect:/admin/movie?message=Successfully updated " + movie.getName();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteMovie(@PathVariable("id") Long id) {
        // before deleting the parent object, clear childlist because movielists I don't want to delete
        this.movieService.getMovie(id).getListOfCustomList().clear();
        // now delete this fucking entity
        this.movieService.deleteMovie(id);
        return "redirect:/admin/movie?message=" + "Deleted!";
    }

}
