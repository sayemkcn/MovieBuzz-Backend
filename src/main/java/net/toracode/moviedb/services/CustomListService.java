package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.Movie;
import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.repositories.CustomListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private static final String FIELD_NAME = "uniqueId";

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

    @Transactional(readOnly = true)
    public List<CustomList> getByUser(User user) {
        return this.customListRepository.findByUser(user);
    }

    // check if a list already cotains a movie
    public boolean isMovieAlreadyExistsOnList(List<Movie> movieList, Movie movie) {
        for (Movie m : movieList) {
            if (m.getUniqueId() == movie.getUniqueId()) {
                return true;
            }
        }
        return false;
    }

    // check if a List of custom list has already a list
    public boolean isAlreadyExists(CustomList list,User user) {
        for (User u : list.getFollowerList()) {
            if (u.getUniqueId() == user.getUniqueId())
                return true;
        }
        return false;
    }

    // removes an item form the movie list
    public List<Movie> removeFromList(List<Movie> movieList, Movie movie) {
        try {
            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getUniqueId() == movie.getUniqueId())
                    movieList.remove(i);
            }
        } catch (Exception e) {

        }
        return movieList;
    }

    // find public lists
    public List<CustomList> getPublicLists(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, FIELD_NAME);
        return this.customListRepository.findByTypeIgnoreCase("public", pageRequest);
    }
}
