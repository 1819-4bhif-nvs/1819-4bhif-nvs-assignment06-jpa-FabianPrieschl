package at.htl.movies.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity(name = "CrewMember")

@NamedQueries({
        @NamedQuery(name = "CreqMember.findAllActors", query = "select cm from CrewMember cm where CrewMember.role = 'Actor'"),
        @NamedQuery(name = "CrewMember.findAllDirectors", query = "select cm from CrewMember cm where CrewMember.role = 'Director'"),
        @NamedQuery(name = "CrewMember.findAll", query = "select cm from CrewMember cm")
})
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String role;

    @ManyToMany(mappedBy = "crewMembers")
    private Set<Movie> movies;

    public CrewMember() {
    }

    public CrewMember(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() {
        return id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
