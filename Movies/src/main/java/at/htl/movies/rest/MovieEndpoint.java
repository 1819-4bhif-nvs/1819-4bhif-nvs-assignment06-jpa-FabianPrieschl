package at.htl.movies.rest;

import at.htl.movies.model.Movie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("movie")
@Stateless
public class MovieEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(em.createNamedQuery("Movie.findAll", Movie.class).getResultList()).build();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("id") long id) {
        Movie m = em.find(Movie.class, id);
        if(m != null){
            return Response.ok().entity(m).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        Movie m = em.find(Movie.class, id);
        if(m != null){
            em.remove(m);
        }
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Movie m){
        try {
            em.persist(m);
            em.flush();
            em.refresh(m);
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
        return Response.ok().entity(m).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(Movie m){
        m = em.merge(m);
        em.flush();
        em.refresh(m);
        return Response.ok().entity(m).build();
    }
}
