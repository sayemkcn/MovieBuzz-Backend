package net.toracode.moviedb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity(name = "custom_list")
public class CustomList extends BaseEntity {
    private String title;
    private String description;
    private String type;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listOfCustomList")
    private List<Movie> movieList;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> followerList;

//    @PreRemove
//    private void removeGroupsFromUsers() {
//        for (Movie m : movieList) {
//            m.getListOfCustomList().remove(this);
//        }
//    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<User> followerList) {
        this.followerList = followerList;
    }

    @Override
    public String toString() {
        return "CustomList{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", movieList=" + movieList +
                ", user=" + user +
                ", followerList=" + followerList +
                '}';
    }
}
