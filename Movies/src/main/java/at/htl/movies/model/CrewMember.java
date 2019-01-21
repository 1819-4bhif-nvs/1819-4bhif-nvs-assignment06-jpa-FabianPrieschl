package at.htl.movies.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.*;
import java.util.Set;

@XmlRootElement
@Entity(name = "CrewMember")
@NamedQueries({
        @NamedQuery(name = "CrewMember.findAllActors", query = "select cm from CrewMember cm where cm.crewRole = 'Actor'"),
        @NamedQuery(name = "CrewMember.findAllDirectors", query = "select cm from CrewMember cm where cm.crewRole = 'Director'"),
        @NamedQuery(name = "CrewMember.findAll", query = "select cm from CrewMember cm")
})
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String crewRole;

    @JsonIgnore
    @ManyToMany(mappedBy = "crewMembers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Movie> movies;

    public CrewMember() {
    }

    public CrewMember(String firstName, String lastName, String crewRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.crewRole = crewRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCrewRole() {
        return crewRole;
    }

    public void setCrewRole(String crewRole) {
        this.crewRole = crewRole;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie m){
        if(!movies.contains(m))
            movies.add(m);
        if(!m.getCrewMembers().contains(this))
            m.addCrewMember(this);
    }

    public void removeMovie(Movie m){
        if(movies.contains(m))
            movies.remove(m);
        if(m.getCrewMembers().contains(this))
            m.removeCrewMember(this);
    }
}
