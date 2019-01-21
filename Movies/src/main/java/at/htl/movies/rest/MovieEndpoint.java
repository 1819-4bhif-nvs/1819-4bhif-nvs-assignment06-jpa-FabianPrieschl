package at.htl.movies.rest;

import at.htl.movies.model.CrewMember;
import at.htl.movies.model.Movie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
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
        List<Movie> list = em.createNamedQuery("Movie.findAll", Movie.class).getResultList();
        GenericEntity entity = new GenericEntity<List<Movie>>(list){};
        if(list != null && !list.isEmpty())
            return Response.ok(entity).build();
        else
            return Response.noContent().build();
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

    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") long id){
        Movie m = em.find(Movie.class, id);
        try {
            if(m != null){
                List<CrewMember> crewMembers = new LinkedList(m.getCrewMembers());
                for (CrewMember cm : crewMembers) {
                    cm.removeMovie(m);
                }
                em.remove(m);
            }
        }
        catch (Exception e){
            return Response.status(404).build();
        }
        return Response.noContent().entity(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMovie(Movie m){
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
    public Response putMovie(Movie m){
        m = em.merge(m);
        em.flush();
        em.refresh(m);
        return Response.ok().entity(m).build();
    }
}
