package at.htl.movies.rest;

import at.htl.movies.model.CrewMember;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crewMember")
@Stateless
public class CrewMemberEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(em.createNamedQuery("CrewMember.findAll", CrewMember.class).getResultList()).build();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrewMembers(@PathParam("id") long id) {
        CrewMember cm = em.find(CrewMember.class, id);
        if(cm != null){
            return Response.ok().entity(cm).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        CrewMember cm = em.find(CrewMember.class, id);
        if(cm != null){
            em.remove(cm);
        }
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(CrewMember cm){
        try {
            em.persist(cm);
            em.flush();
            em.refresh(cm);
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
        return Response.ok().entity(cm).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(CrewMember cm){
        cm = em.merge(cm);
        em.flush();
        em.refresh(cm);
        return Response.ok().entity(cm).build();
    }
}
