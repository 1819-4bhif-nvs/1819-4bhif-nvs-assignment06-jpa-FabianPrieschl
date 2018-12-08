package at.htl.movies.rest;

import at.htl.movies.model.MovieActor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("movieActor")
@Stateless
public class MovieActorsEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieActor> getAll() {
        return em.createNamedQuery("MovieActor.findAll", MovieActor.class).getResultList();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MovieActor getMovieActor(@PathParam("id") long id) {
        return em.find(MovieActor.class, id);
    }
}
