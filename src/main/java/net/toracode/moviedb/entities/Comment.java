package net.toracode.moviedb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by sayemkcn on 11/15/16.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment extends BaseEntity {
    private String commentBody;
    @JsonIgnore
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentBody='" + commentBody + '\'' +
                ", movie=" + movie +
                ", user=" + user +
                '}';
    }
}
