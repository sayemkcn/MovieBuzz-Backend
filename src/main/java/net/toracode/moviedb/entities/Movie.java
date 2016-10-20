package net.toracode.moviedb.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Movie extends BaseEntity {
	private String name;
	private String storyLine;
	private String language;
	private byte[] image;
	private String country;
	private String genere;
	private String trailerUrl;
	private Date releaseDate;
	private String duration;
	private String budget;
	private char rated;
	private String productionHouse;
	@ManyToMany
	private List<Person> castAndCrewList;
	@OneToMany
	private List<Review> reviewList;

}
