package net.toracode.moviedb.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Person extends BaseEntity {
	private String name;
	private String[] designations;
	private Date birthDate;
	private String bio;
	private String[] awards;
	private String links;
	@ManyToMany
	private List<Movie> movieList;
}
