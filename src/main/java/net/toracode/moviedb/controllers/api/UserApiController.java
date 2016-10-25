package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sayemkcn on 10/25/16.
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{accountId}", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@PathVariable("accountId") String accountId, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "email", required = false) String email) {
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null) {
            user = new User();
            user.setAccountId(accountId);
            // check if params aren't passed for first time reg
            if (name == null)
                user.setName("Anonymous");
            else
                user.setName(name);
            if (email == null)
                user.setEmail("anonymous@toracode.net");
            else
                user.setEmail(email);
            this.userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/update/{accountId}", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@PathVariable("accountId") String accountId, @RequestParam("name") String name, @RequestParam("email") String email) {
        if (name == null || name.length() < 3)
            return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null)
            return new ResponseEntity<User>(HttpStatus.NOT_MODIFIED);
        else {
            user.setName(name);
            user.setEmail(email);
            user = this.userService.saveUser(user);
        }
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }
}
