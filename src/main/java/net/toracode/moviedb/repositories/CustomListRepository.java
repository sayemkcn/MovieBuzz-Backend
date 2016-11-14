package net.toracode.moviedb.repositories;

import net.toracode.moviedb.entities.CustomList;
import net.toracode.moviedb.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sayemkcn on 10/31/16.
 */
@Repository
public interface CustomListRepository extends JpaRepository<CustomList, Long> {
    List<CustomList> findByUser(User user);

    List<CustomList> findByTypeIgnoreCase(String type, Pageable pageable);
}
