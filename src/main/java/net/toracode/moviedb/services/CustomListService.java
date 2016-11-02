package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.repositories.CustomListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sayemkcn on 10/31/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomListService {
    @Autowired
    private CustomListRepository customListRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CustomList saveList(CustomList customList) {
        return this.customListRepository.save(customList);
    }

    @Transactional(readOnly = true)
    public CustomList getOne(Long id) {
        return this.customListRepository.getOne(id);
    }


    public List<CustomList> getByUser(User user) {
        return this.customListRepository.findByUser(user);
    }

    public boolean isMovieAlreadyExistsOnList(List<Movie> movieList, Movie movie) {
        for (Movie m : movieList) {
            if (m.getUniqueId() == movie.getUniqueId()) {
                return true;
            }
        }
        return false;
    }
}
