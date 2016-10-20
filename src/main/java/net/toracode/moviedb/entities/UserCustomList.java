package net.toracode.moviedb.entities;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserCustomList extends BaseEntity{
	private String title;
	private String description;
	private String type;
	private List<Movie> movieList;
}
