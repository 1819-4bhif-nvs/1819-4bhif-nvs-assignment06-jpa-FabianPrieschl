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
        Response response = this.webTarget.path("/movie/find/1").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        assertThat(jsonObject.getString("title"), is("Die Verurteilten"));
        assertThat(jsonObject.getString("genre"), is("Drama"));
    }

    @Test
    public void test03_deleteMovie(){
        Response response = webTarget.path("/movie/1").request().delete();
        assertThat(response.getStatus(),is(204));
    }
}
