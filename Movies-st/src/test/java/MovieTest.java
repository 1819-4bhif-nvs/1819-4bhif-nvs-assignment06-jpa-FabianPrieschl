import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieTest {

    private Client client;
    private WebTarget webTarget;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8085/movies/api/");
    }

    @Test
    public void test01_getAllMovies() {
        Response response = this.webTarget.path("/movie/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThan(1));
    }

    @Test
    public void test02_getSingleMovie() {
        Response response = this.webTarget.path("/movie/find/2").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        assertThat(jsonObject.getString("title"), is("Die Verurteilten"));
        assertThat(jsonObject.getString("genre"), is("Drama"));
    }

    @Test
    public void test03_getAllDirectors(){
        Response response = this.webTarget.path("/director/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThan(1));
    }

    @Test
    public void test04_getSingleDirector() {
        Response response = this.webTarget.path("/director/find/1").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        assertThat(jsonObject.getString("firstName"), is("Frank"));
        assertThat(jsonObject.getString("lastName"), is("Darabont"));
        assertThat(jsonObject.getInt("amountOfMovies"), is(11));
    }

    @Test
    public void test05_createDirector(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject newDirector = builder.add("firstName", "Max").add("lastName", "Mustermann").add("amountOfMovies", 7).build();
        Response response = this.webTarget.path("director/post").request().post(Entity.json(newDirector));
        assertThat(response.getStatus(), is(200));
        long id = Long.valueOf(response.readEntity(String.class));
        JsonObject dir = this.webTarget.path("/director/find/" + id).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(dir.getString("firstName"), is("Max"));
        assertThat(dir.getString("lastName"), is("Mustermann"));
        assertThat(dir.getInt("amountOfMovies"), is(7));
    }

    @Test
    public void test06_updateDirector(){
        JsonObject director = Json.createObjectBuilder()
                .add("firstName", "Alfred")
                .add("lastName", "Hitchcock")
                .add("amountOfMovies", 70)
                .build();
        this.webTarget.path("/director/put/1").request().put(Entity.json(director));
        JsonObject dir = this.webTarget.path("/director/find/1").request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(dir.getString("firstName"), is("Alfred"));
        assertThat(dir.getString("lastName"), is("Hitchcock"));
        assertThat(dir.getInt("amountOfMovies"), is(70));
    }

    @Test
    public void test07_deleteDirector(){
        Response response = this.webTarget.path("/director/delete/25").request().delete();
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void test08_getAllActors(){
        Response response = this.webTarget.path("/actor/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThan(1));
    }

    @Test
    public void test09_getSingleActor() {
        Response response = this.webTarget.path("/actor/find/3").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        assertThat(jsonObject.getString("firstName"), is("Tim"));
        assertThat(jsonObject.getString("lastName"), is("Robbins"));
        assertThat(jsonObject.getString("gender"), is("male"));
    }

    @Test
    public void test10_createActor(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject newActor = builder.add("firstName", "Erika").add("lastName", "Musterfrau").add("gender", "female").build();
        Response response = this.webTarget.path("actor/post").request().post(Entity.json(newActor));
        assertThat(response.getStatus(), is(200));
        long id = Long.valueOf(response.readEntity(String.class));
        JsonObject ac = this.webTarget.path("/actor/find/" + id).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(ac.getString("firstName"), is("Erika"));
        assertThat(ac.getString("lastName"), is("Musterfrau"));
        assertThat(ac.getString("gender"), is("female"));
    }

    @Test
    public void test11_updateActor(){
        JsonObject actor = Json.createObjectBuilder()
                .add("firstName", "Johnny")
                .add("lastName", "Depp")
                .add("gender", "male")
                .build();
        this.webTarget.path("/actor/put/3").request().put(Entity.json(actor));
        JsonObject ac = this.webTarget.path("/actor/find/3").request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(ac.getString("firstName"), is("Johnny"));
        assertThat(ac.getString("lastName"), is("Depp"));
        assertThat(ac.getString("gender"), is("male"));
    }

    @Test
    public void test12_deleteActor(){
        Response response = this.webTarget.path("/actor/delete/26").request().delete();
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void test13_getAllMovieActors() {
        Response response = this.webTarget.path("/movieActor/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThan(1));
    }

    @Test
    public void test14_getSingleMovieActor() {
        Response response = this.webTarget.path("/movieActor/find/7").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        assertThat(jsonObject.getJsonObject("movie").getString("title"), is("Die Verurteilten"));
        assertThat(jsonObject.getJsonObject("actor").getString("firstName"), is("Morgan"));
        assertThat(jsonObject.getJsonObject("actor").getString("lastName"), is("Freeman"));
    }
}
