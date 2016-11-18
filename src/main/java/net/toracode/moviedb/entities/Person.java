package net.toracode.moviedb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Entity
public class Person extends BaseEntity {
    private String name;
    private String[] designations;
    private Date birthDate;
    private String bio;
    private String[] awards;
    private String[] socialLinks;
    @Lob
    @JsonIgnore
    @Basic(fetch = FetchType.LAZY,optional = true)
    private byte[] image;

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


    public String[] getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(String[] socialLinks) {
        this.socialLinks = socialLinks;
    }

    public String commaSeperatedArrayElements(String[] array) {
        return String.join(",", array);
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", designations=" + Arrays.toString(designations) +
                ", birthDate=" + birthDate +
                ", bio='" + bio + '\'' +
                ", awards=" + Arrays.toString(awards) +
                ", socialLinks=" + Arrays.toString(socialLinks) +
                '}';
    }

}
