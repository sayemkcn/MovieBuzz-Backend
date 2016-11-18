package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sayemkcn on 11/18/16.
 */
@RestController
@RequestMapping("/api/person")
public class PersonApiController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/movie/{movieId}")
    public ResponseEntity<List<Person>> castAndCrewListByMovie(@PathVariable("movieId") Long movieId) {
        List<Person> castAndCrewList = this.personService.getPersonListByMovie(movieId);
        if (castAndCrewList == null || castAndCrewList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(castAndCrewList, HttpStatus.OK);
    }

}
