package at.htl.movies.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "Actor")

@NamedQueries({
        @NamedQuery(name = "Actor.findAll", query = "select a from Actor a")
})
public class Actor extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gender;

    public Actor() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Actor(String firstName, String lastName, String gender) {
        super(firstName, lastName);
        this.gender = gender;
    }
}
