package at.htl.movies.model;

import javax.persistence.Entity;

@Entity(name = "UserRating")
public class UserRating extends Rating{

    private String userName;
    private String version;

    public UserRating() {
    }

    public UserRating(double points, Movie movie, String userName, String version) {
        super(points, movie);
        this.userName = userName;
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
