package net.toracode.moviedb.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.toracode.moviedb.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.repositories.PersonRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PersonService {

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private MovieService movieService;

    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return this.personRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> getPersonListByMovie(Long movieId) {
        Movie movie = this.movieService.getMovie(movieId);
        if (movie != null)
            return movie.getCastAndCrewList();
        return null;
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersonsPaginated(Integer page, Integer size) {
        Page<Person> personList = this.personRepo.findAll(new PageRequest(page, size));
        return personList.getContent();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Person save(Person person) {
        return personRepo.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> personListByIds(Long[] ids) {
        return personRepo.findByIdIn(ids);
    }

    @Transactional(readOnly = true)
    public Person getPersonById(Long id) {
        return this.personRepo.findOne(id);
    }

    public List<Person> removePerson(List<Person> personList, Long personId) {
        List<Person> pList = new ArrayList<>();
        for (Person person : personList) {
            if (!person.getUniqueId().equals(personId)) {
                pList.add(person);
            }
        }
        return pList;
    }

    public boolean isAlreadyExists(List<Person> personList, Long personId) {
        for (Person person : personList) {
            if (person.getUniqueId().equals(personId)) return true;
        }
        return false;
    }

    public List<Person> searchPersonByNamePaginated(String phrase, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC, "name");
        return this.personRepo.findByNameContaining(phrase, pageRequest).getContent();
    }
}
