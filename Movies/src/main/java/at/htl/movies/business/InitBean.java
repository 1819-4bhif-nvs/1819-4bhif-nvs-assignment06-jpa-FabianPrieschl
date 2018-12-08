package at.htl.movies.business;

import at.htl.movies.model.Actor;
import at.htl.movies.model.Director;
import at.htl.movies.model.Movie;
import at.htl.movies.model.MovieActor;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Singleton
@Startup
public class InitBean {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        Director d1 = new Director("Frank", "Darabont", 11);
        Movie m1 = new Movie("Die Verurteilten", "Drama", 9.3, LocalDate.parse("1995-03-09"), d1);
        Actor a1 = new Actor("Tim", "Robbins", "male");
        Actor a2 = new Actor("Morgan", "Freeman", "male");
        Actor a3 = new Actor("Bob", "Gunton", "male");
        MovieActor ma1 = new MovieActor(m1, a1);
        MovieActor ma2 = new MovieActor(m1, a2);
        MovieActor ma3 = new MovieActor(m1, a3);

        Director d2 = new Director("Francis Ford", "Coppola", 35);
        Movie m2 = new Movie("Der Pate", "Drama", 9.2, LocalDate.parse("1972-08-24"), d2);
        Actor a4 = new Actor("Marlon", "Brando", "male");
        Actor a5 = new Actor("Al", "Pacino", "male");
        Actor a6 = new Actor("James", "Caan", "male");
        MovieActor ma4 = new MovieActor(m2, a4);
        MovieActor ma5 = new MovieActor(m2, a5);
        MovieActor ma6 = new MovieActor(m2, a6);

        Director d3 = new Director("Christopher", "Nolan", 14);
        Movie m3 = new Movie("The Dark Knight", "Action", 9.0, LocalDate.parse("2008-08-21"), d3);
        Actor a7 = new Actor("Christian", "Bale", "male");
        Actor a8 = new Actor("Heath", "Ledger", "male");
        Actor a9 = new Actor("Aaron", "Eckhardt", "male");
        MovieActor ma7 = new MovieActor(m3, a7);
        MovieActor ma8 = new MovieActor(m3, a8);
        MovieActor ma9 = new MovieActor(m3, a9);

        em.persist(d1);
        em.persist(m1);
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(ma1);
        em.persist(ma2);
        em.persist(ma3);

        em.persist(d2);
        em.persist(m2);
        em.persist(a4);
        em.persist(a5);
        em.persist(a6);
        em.persist(ma4);
        em.persist(ma5);
        em.persist(ma6);

        em.persist(d3);
        em.persist(m3);
        em.persist(a7);
        em.persist(a8);
        em.persist(a9);
        em.persist(ma7);
        em.persist(ma8);
        em.persist(ma9);
    }
}
