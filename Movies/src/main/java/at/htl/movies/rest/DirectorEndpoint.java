package at.htl.movies.rest;

import at.htl.movies.model.Director;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("director")
@Stateless
public class DirectorEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Director> getAll() {
        return em.createNamedQuery("Director.findAll", Director.class).getResultList();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Director getDirector(@PathParam("id") long id) {
        return em.find(Director.class, id);
    }

    @Path("delete/{id}")
    @DELETE
    public void del(@PathParam("id") long id) {
        Director d = em.find(Director.class, id);
        em.remove(d);
    }

    @Path("put/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") long id, Director director) {
        Director d = em.find(Director.class, id);
        d.setFirstName(director.getFirstName());
        d.setLastName(director.getLastName());
        d.setAmountOfMovies(director.getAmountOfMovies());
        em.merge(d);
    }

    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Director director){
        em.persist(director);
        em.flush();
        return director.getId();
    }
}
