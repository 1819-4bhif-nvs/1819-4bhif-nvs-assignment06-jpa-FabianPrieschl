package at.htl.movies.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "Director")

@NamedQueries({
        @NamedQuery(name = "Director.findAll", query = "select d from Director d")
})
public class Director extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amountOfMovies;

    public Director() {
    }

    public Director(String firstName, String lastName, int amountOfMovies) {
        super(firstName, lastName);
        this.amountOfMovies = amountOfMovies;
    }

    @Override
    public Long getId() {
        return id;
    }

    public int getAmountOfMovies() {
        return amountOfMovies;
    }

    public void setAmountOfMovies(int yearsOfExperience) {
        this.amountOfMovies = yearsOfExperience;
    }
}
