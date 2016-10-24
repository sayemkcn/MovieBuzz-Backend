package net.toracode.moviedb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.toracode.moviedb.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("FROM Person P WHERE P.uniqueId IN :ids")
	List<Person> findByIdIn(@Param("ids") Long[] ids);
}
