package at.htl.movies.rest;

import at.htl.movies.model.Actor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("actor")
@Stateless
public class ActorEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> getAll() {
        return em.createNamedQuery("Actor.findAll", Actor.class).getResultList();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Actor getActor(@PathParam("id") long id) {
        return em.find(Actor.class, id);
    }

    @Path("delete/{id}")
    @DELETE
    public void del(@PathParam("id") long id) {
        Actor a = em.find(Actor.class, id);
        em.remove(a);
    }

    @Path("put/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") long id, Actor actor) {
        Actor a = em.find(Actor.class, id);
        a.setFirstName(actor.getFirstName());
        a.setLastName(actor.getLastName());
        a.setGender(actor.getGender());
        em.merge(a);
    }

    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Actor actor){
        em.persist(actor);
        em.flush();
        return actor.getId();
    }
}
