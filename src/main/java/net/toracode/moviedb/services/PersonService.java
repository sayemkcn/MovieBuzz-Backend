package net.toracode.moviedb.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.repositories.PersonRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class PersonService {
	
	@Autowired
	private PersonRepository personRepo;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public Person save(Person person){
		return personRepo.save(person);
	}
	
	@Transactional(readOnly=true)
	public List<Person> findAll(){
		return personRepo.findAll();
	}
	
	public List<Person> personListByIds(Long[] ids){
//		Iterable<Person> personIterable = personRepo.findByIdIn(Arrays.asList(ids), new Sort(Sort.Direction.ASC,"userId"));
//		List<Person> personList = new ArrayList<>();
//		for(Person person:personIterable){
//			personList.add(person);
//		}
		return personRepo.findByIdIn(ids);
	}
}
