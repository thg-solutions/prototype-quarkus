package de.thg.prototype;


import de.thg.prototype.persistence.Person;
import org.graalvm.nativeimage.impl.InternalPlatform;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/people")
public class PeopleResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(APPLICATION_JSON)
    public List<Person> findAll() {
        return Person.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Person findById(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @GET
    @Path("/search/findByLastname")
    @Produces(APPLICATION_JSON)
    public List<Person> findByLastname(@QueryParam("name") String lastname) {
        return Person.findByLastname(lastname);
    }

    @GET
    @Path("/search/findByFirstname")
    @Produces(APPLICATION_JSON)
    public List<Person> findByFirstname(@QueryParam("name") String firstname) {
        return Person.findByFirstname(firstname);
    }

    @GET
    @Path("/search/findByLastnameStartingWith")
    @Produces(APPLICATION_JSON)
    public List<Person> findByLastnameStartingWith(@QueryParam("abbr") String abbr) {
        return Person.findByLastnameStartingWith(abbr);
    }

    @GET
    @Path("/search/deriveResourceUri")
    @Produces(TEXT_PLAIN)
    public String deriveResourceUri() {
        return uriInfo.getBaseUriBuilder().path(PeopleResource.class).path("this_is_for_testing_only").build().toString();
    }

}
