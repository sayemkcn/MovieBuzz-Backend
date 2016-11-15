package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.Comment;
import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sayemkcn on 11/15/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    private static String FIELD_NAME = "uniqueId";

    @Transactional(readOnly = false)
    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment getOne(Long id) {
        return this.commentRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByCustomList(CustomList list, int page, int size) {
        return this.commentRepository.findByList(list, new PageRequest(page, size, Sort.Direction.DESC, FIELD_NAME));
    }

    @Transactional(readOnly = true)
    public List<Comment> getByUser(User user, int page, int size) {
        return this.commentRepository.findByUser(user, new PageRequest(page, size, Sort.Direction.DESC, FIELD_NAME));
    }

    @Transactional(readOnly = false)
    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

}
