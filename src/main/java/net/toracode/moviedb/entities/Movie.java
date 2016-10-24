package net.toracode.moviedb.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Movie extends BaseEntity {
	private String name;
	private String storyLine;
	private String language;
	@Lob
	private byte[] image;
	private String industry;
	private String genere;
	private String trailerUrl;
	private Date releaseDate;
	private String duration;
	private String budget;
	private char rated;
	private String productionHouse;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Person> castAndCrewList;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Review> reviewList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoryLine() {
		return storyLine;
	}

	public void setStoryLine(String storyLine) {
		this.storyLine = storyLine;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public char getRated() {
		return rated;
	}

	public void setRated(char rated) {
		this.rated = rated;
	}

	public String getProductionHouse() {
		return productionHouse;
	}

	public void setProductionHouse(String productionHouse) {
		this.productionHouse = productionHouse;
	}

	public List<Person> getCastAndCrewList() {
		return castAndCrewList;
	}

	public void setCastAndCrewList(List<Person> castAndCrewList) {
		this.castAndCrewList = castAndCrewList;
	}

	public List<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}

	@Override
	public String toString() {
		return "Movie [name=" + name + ", storyLine=" + storyLine + ", language=" + language + ", image="
				+ Arrays.toString(image) + ", country=" + industry + ", genere=" + genere + ", trailerUrl=" + trailerUrl
				+ ", releaseDate=" + releaseDate + ", duration=" + duration + ", budget=" + budget + ", rated=" + rated
				+ ", productionHouse=" + productionHouse + ", castAndCrewList=" + castAndCrewList + ", reviewList="
				+ reviewList + "]";
	}

}
