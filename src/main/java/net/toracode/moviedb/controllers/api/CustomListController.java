package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.services.CustomListService;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sayemkcn on 10/31/16.
 */
@RestController
@RequestMapping(value = "/api/list")
public class CustomListController {

    @Autowired
    private CustomListService customListService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CustomList> createList(@ModelAttribute CustomList customList, BindingResult bindingResult,
                                                 @RequestParam("accountId") String accountId) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<CustomList>(HttpStatus.BAD_REQUEST);
        User user = this.userService.getUserByAccountId(accountId);
        // if user not registered
        if (user == null)
            return new ResponseEntity<CustomList>(HttpStatus.INTERNAL_SERVER_ERROR);
        // if list title or type is empty
        if (customList.getTitle().isEmpty() || customList.getTitle().length() < 3)
            return new ResponseEntity<CustomList>(HttpStatus.NOT_ACCEPTABLE);
        if (customList.getType().isEmpty() || customList.getType().length() < 3)
            return new ResponseEntity<CustomList>(HttpStatus.NOT_ACCEPTABLE);

        customList.setUser(user);
        customList = this.customListService.saveList(customList);
        return new ResponseEntity<CustomList>(customList, HttpStatus.CREATED);
    }

    // returns all of the list of a user
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<CustomList>> customListListByAccountId(@RequestParam("accountId") String accountId) {
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null)
            return new ResponseEntity<List<CustomList>>(HttpStatus.BAD_REQUEST);
        List<CustomList> customListList = this.customListService.getByUser(user);
        if (customListList == null || customListList.isEmpty())
            return new ResponseEntity<List<CustomList>>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<CustomList>>(customListList, HttpStatus.FOUND);

    }

    @RequestMapping(value = "/{listId}/add/{movieId}", method = RequestMethod.POST)
    public ResponseEntity<List<Movie>> addMovieToList(@PathVariable("movieId") Long movieId,
                                                      @PathVariable("listId") Long listId) {
        Movie movie = this.movieService.getMovie(movieId);
        CustomList customList = this.customListService.getOne(listId);
        if (customList == null)
            return new ResponseEntity<List<Movie>>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (customList.getMovieList() == null) {
            customList.setMovieList(new ArrayList<>());
        }
        customList.getMovieList().add(movie);
        customList = this.customListService.saveList(customList);
        return new ResponseEntity<List<Movie>>(customList.getMovieList(), HttpStatus.OK);
    }

}
