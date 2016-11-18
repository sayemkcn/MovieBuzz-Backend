package net.toracode.moviedb.controllers.api;

import net.toracode.moviedb.entities.Comment;
import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.services.CommentService;
import net.toracode.moviedb.services.CustomListService;
import net.toracode.moviedb.services.MovieService;
import net.toracode.moviedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sayemkcn on 11/15/16.
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomListService customListService;

    // returns a specific comment
    @RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable("commentId") Long commentId) {
        Comment comment = this.commentService.getOne(commentId);
        if (comment == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(comment, HttpStatus.FOUND);
    }

    // create a comment
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Comment> saveComment(@ModelAttribute Comment comment, BindingResult bindingResult,
                                               @RequestParam("listId") Long listId,
                                               @RequestParam("accountId") String accountId) {
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null) // if user not registered // not found
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        CustomList list = this.customListService.getOne(listId);
        if (list == null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        comment.setUser(user);
        comment.setList(list);
        comment = this.commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // edit a comment
    @RequestMapping(value = "/edit/{commentId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> editComment(@PathVariable("commentId") Long commentId,
                                               @RequestParam("commentBody") String commentBody,
                                               @RequestParam("accountId") String accountId) {
        if (commentBody == null || commentBody.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        Comment comment = this.commentService.getOne(commentId);
        if (comment == null)
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        if (!comment.getUser().getAccountId().equals(accountId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        comment.setCommentBody(commentBody);
        comment = this.commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.ACCEPTED);
    }

    // delete comment
    @RequestMapping(value = "/delete/{commentId}", method = RequestMethod.POST)
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId,
                                        @RequestParam("accountId") String accountId) {
        Comment comment = this.commentService.getOne(commentId);
        if (!comment.getUser().getAccountId().equals(accountId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        this.commentService.delete(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    // all comments of a specific list paginated
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> allCommentsOfACustomList(@RequestParam("listId") Long listId) {
        CustomList list = this.customListService.getOne(listId);
        List<Comment> commentList = this.commentService.getByCustomList(list);
        if (commentList == null || commentList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> allCommentsOfAUser(@PathVariable("accountId") String accountId,
                                                            @RequestParam("page") int page) {
        User user = this.userService.getUserByAccountId(accountId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<Comment> commentList = this.commentService.getByUser(user, page, 10);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
}
