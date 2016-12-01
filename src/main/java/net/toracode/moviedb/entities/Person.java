package net.toracode.moviedb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Person extends BaseEntity {
    @NotNull
    @Size(min = 3,message = "Name can not be less than 3 character.")
    private String name;
    @Size(min = 1,message = "Please enter designations separated by comma\'s")
    private String[] designations;
    private Date birthDate;
    @Size(min = 30,message = "Please describe a little more about this person. Bio can not be less than 30 characters.")
    @Column(columnDefinition = "TEXT")
    private String bio;
    private String[] awards;
    private String[] socialLinks;
    @JsonIgnore
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(length = 20000000)
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

    public String getCommaSeperatedDesignations() {
        return StringUtils.join(this.designations, ", ");
    }

}
