package at.htl.movies.business;

import at.htl.movies.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Singleton
@Startup
public class InitBean {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        List<CrewMember> cm1 = new LinkedList<>();
        CrewMember d1 = new CrewMember("Frank", "Darabont", "Director");
        CrewMember a1 = new CrewMember("Tim", "Robbins", "Actor");
        CrewMember a2 = new CrewMember("Morgan", "Freeman", "Actor");
        CrewMember a3 = new CrewMember("Bob", "Gunton", "Actor");
        cm1.add(d1);
        cm1.add(a1);
        cm1.add(a2);
        cm1.add(a3);
        Movie m1 = new Movie("Die Verurteilten", "Drama", LocalDate.parse("1995-03-09"), cm1);

        List<Rating> rl1 = new LinkedList<>();
        Rating r1 = new UserRating(5.0, m1, "Josef P.", "Prime Video");
        Rating r2 = new CriticsRating(5.0, m1, "TIME Magazine", "Richard", "Schickel");
        rl1.add(r1);
        rl1.add(r2);
        m1.setRatings(rl1);

        List<CrewMember> cm2 = new LinkedList<>();
        CrewMember d2 = new CrewMember("Francis Ford", "Coppola", "Director");
        CrewMember a4 = new CrewMember("Marlon", "Brando", "Actor");
        CrewMember a5 = new CrewMember("Al", "Pacino", "Actor");
        CrewMember a6 = new CrewMember("James", "Caan", "Actor");
        cm2.add(d2);
        cm2.add(a4);
        cm2.add(a5);
        cm2.add(a6);
        Movie m2 = new Movie("Der Pate", "Drama", LocalDate.parse("1972-08-24"), cm2);

        List<Rating> rl2 = new LinkedList<>();
        Rating r3 = new UserRating(5.0, m2, "Alberto", "Blu-ray");
        Rating r4 = new CriticsRating(5.0, m2, "Chicago Tribune", "Gene", "Siskel");
        rl2.add(r3);
        rl2.add(r4);
        m2.setRatings(rl2);

        List<CrewMember> cm3 = new LinkedList<>();
        CrewMember d3 = new CrewMember("Christopher", "Nolan", "Director");
        CrewMember a7 = new CrewMember("Christian", "Bale", "Actor");
        CrewMember a8 = new CrewMember("Heath", "Ledger", "Actor");
        CrewMember a9 = new CrewMember("Aaron", "Eckhardt", "Actor");
        cm3.add(d3);
        cm3.add(a7);
        cm3.add(a8);
        cm3.add(a9);
        Movie m3 = new Movie("The Dark Knight", "Action", LocalDate.parse("2008-08-21"), cm3);

        List<Rating> rl3 = new LinkedList<>();
        Rating r5 = new UserRating(5.0, m3, "Jaqueline Schuster", "DVD");
        Rating r6 = new CriticsRating(5.0, m3, "CNN.com", "Tom", "Charity");
        rl3.add(r5);
        rl3.add(r6);
        m3.setRatings(rl3);

        em.persist(d1);
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(r1);
        em.persist(r2);
        em.persist(m1);

        em.persist(d2);
        em.persist(a4);
        em.persist(a5);
        em.persist(a6);
        em.persist(r3);
        em.persist(r4);
        em.persist(m2);

        em.persist(d3);
        em.persist(a7);
        em.persist(a8);
        em.persist(a9);
        em.persist(r5);
        em.persist(r6);
        em.persist(m3);
    }
}
