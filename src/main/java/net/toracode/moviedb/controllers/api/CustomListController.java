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

    // create a list for a user
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

    // returns all public list available
    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public ResponseEntity<List<CustomList>> allPublicLists(@RequestParam("page") int page) {
        List<CustomList> customListList = this.customListService.getPublicLists(page, 10);
        if (customListList == null || customListList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(customListList, HttpStatus.OK);
    }

    // Follows a public list // find the list and add to following list of user
    @RequestMapping(value = "/follow/{listId}", method = RequestMethod.POST)
    public ResponseEntity<CustomList> followList(@PathVariable("listId") Long listId, @RequestParam("accountId") String accountId) {
        User user = this.userService.getUserByAccountId(accountId);
        CustomList list = this.customListService.getOne(listId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // check if user following list is null
        if (list.getFollowerList() != null) {
            if (this.customListService.isAlreadyExists(list,user)) {
                return new ResponseEntity<>(HttpStatus.MULTI_STATUS);
            }
            list.getFollowerList().add(user);
            this.customListService.saveList(list);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

//    @RequestMapping(value = "/following", method = RequestMethod.GET)
//    public ResponseEntity<List<CustomList>> myFollowingList(@RequestParam("accountId") String accountId) {
//        User user = this.userService.getUserByAccountId(accountId);
//        if (user == null)
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//
//        if (user.getFollowingList() == null || user.getFollowingList().isEmpty())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(user.getFollowingList(), HttpStatus.OK);
//    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> moviesByListId(@PathVariable("listId") Long listId) {
        CustomList list = this.customListService.getOne(listId);
        System.out.println(list.toString());
        if (list == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list.getMovieList(), HttpStatus.OK);
    }

    // adds movie to a list
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
        if (this.customListService.isMovieAlreadyExistsOnList(customList.getMovieList(), movie))
            return new ResponseEntity<List<Movie>>(HttpStatus.CONFLICT);
        customList.getMovieList().add(movie);
        customList = this.customListService.saveList(customList);
        return new ResponseEntity<List<Movie>>(customList.getMovieList(), HttpStatus.OK);
    }

    // remove movie from a list
    @RequestMapping(value = "/{listId}/remove/{movieId}", method = RequestMethod.POST)
    public ResponseEntity<List<Movie>> deleteMovieFromList(@PathVariable("movieId") Long movieId,
                                                           @PathVariable("listId") Long listId) {
        Movie movie = this.movieService.getMovie(movieId);
        CustomList customList = this.customListService.getOne(listId);
        if (customList == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (!this.customListService.isMovieAlreadyExistsOnList(customList.getMovieList(), movie))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        List<Movie> updatedMovieList = this.customListService.removeFromList(customList.getMovieList(), movie);
        customList.setMovieList(updatedMovieList);
        customList = this.customListService.saveList(customList);
        return new ResponseEntity<>(customList.getMovieList(), HttpStatus.OK);
    }


}
