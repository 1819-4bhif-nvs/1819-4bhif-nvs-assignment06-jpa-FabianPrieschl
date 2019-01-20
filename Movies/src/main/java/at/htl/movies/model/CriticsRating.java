package at.htl.movies.model;

import javax.persistence.Entity;

@Entity(name = "CriticsRating")
public class CriticsRating extends Rating {

    private String source;
    private String firstName;
    private String lastName;

    public CriticsRating() {
    }

    public CriticsRating(double points, Movie movie, String source, String firstName, String lastName) {
        super(points, movie);
        this.source = source;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
