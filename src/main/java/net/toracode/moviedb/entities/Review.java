package net.toracode.moviedb.entities;

import javax.persistence.Entity;

@Entity
public class Review extends BaseEntity{
	private String title;
	private String message;
	private byte rating;
}
