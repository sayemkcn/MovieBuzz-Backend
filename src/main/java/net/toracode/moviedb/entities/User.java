package net.toracode.moviedb.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
public class User extends BaseEntity {
	@Column(nullable = false)
	private String name;
	@Email
	private String email;
	@Size(min = 6, max = 50)
	private String password;
	private List<Review> reviewList;
}
