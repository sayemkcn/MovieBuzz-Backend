package net.toracode.moviedb.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Person extends BaseEntity {
	private String name;
	private String[] designations;
	private Date birthDate;
	private String bio;
	private String[] awards;
	private String socialLinks;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Movie> movieList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getDesignations() {
		return designations;
	}

	public void setDesignations(String[] designations) {
		this.designations = designations;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String[] getAwards() {
		return awards;
	}

	public void setAwards(String[] awards) {
		this.awards = awards;
	}

	public String getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(String socialLinks) {
		this.socialLinks = socialLinks;
	}

	public List<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", designations=" + Arrays.toString(designations) + ", birthDate=" + birthDate
				+ ", bio=" + bio + ", awards=" + Arrays.toString(awards) + ", socialLinks=" + socialLinks
				+ ", movieList=" + movieList + "]";
	}

}
