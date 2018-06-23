package consulting.jjs.bestpricebe.resources;

import org.jboss.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/hello")
public class HelloWorldEndpoint {

	Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

	@GET
	@Produces("application/json")
	public Response doGet() {
		LOG.trace("A");
		LOG.debug("B");
		LOG.info("C");
		LOG.warn("D");
		LOG.error("E");
		LOG.fatal("F");
		return Response.ok("{\"msg\":\"Hello from WildFly Swarm with logs\"}").build();
	}
}