package at.htl.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement
@Entity(name = "Movie")
@NamedQueries({
        @NamedQuery(name = "Movie.findAll", query = "select m from Movie m")
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Rating> ratings;

    private LocalDate releaseDate;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<CrewMember> crewMembers;

    public Movie() {
    }

    public Movie(String title, String genre, LocalDate releaseDate, List<CrewMember> crewMembers) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.crewMembers = crewMembers;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public void addCrewMember(CrewMember cm){
        if(!crewMembers.contains(cm))
            crewMembers.add(cm);
        if(!cm.getMovies().contains(this))
            cm.addMovie(this);
    }

    public void removeCrewMember(CrewMember cm){
        if(crewMembers.contains(cm))
            crewMembers.remove(cm);
        if(cm.getMovies().contains(this))
            cm.removeMovie(this);
    }
}
