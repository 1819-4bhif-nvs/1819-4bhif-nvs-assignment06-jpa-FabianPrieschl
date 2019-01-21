package at.htl.movies.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Rating> ratings;

    private LocalDate releaseDate;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Movie_CrewMember",
            joinColumns = { @JoinColumn(name = "movies_id") },
            inverseJoinColumns = { @JoinColumn(name = "crewMembers_id") }
    )
    private Set<CrewMember> crewMembers;

    public Movie() {
    }

    public Movie(String title, String genre, LocalDate releaseDate, Set<CrewMember> crewMembers) {
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

    public Set<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(Set<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
