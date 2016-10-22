package net.toracode.moviedb.entities;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class UserCustomList extends BaseEntity {
	private String title;
	private String description;
	private String type;
	@ElementCollection
	private List<Movie> movieList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

	@Override
	public String toString() {
		return "UserCustomList [title=" + title + ", description=" + description + ", type=" + type + ", movieList="
				+ movieList + "]";
	}

}
