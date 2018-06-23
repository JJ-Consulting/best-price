package consulting.jjs.bestpricebe.filter;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import consulting.jjs.bestpricebe.exception.ResourceNotFoundException;
import org.jboss.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {

  private static final Logger LOG = Logger.getLogger(ResourceNotFoundMapper.class);

  @Override
  public Response toResponse(ResourceNotFoundException exception) {
    LOG.error("API failed with a resource not found exception", exception);
    return Response.status(Response.Status.NOT_FOUND)
            .entity(new SimpleMessage(exception.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }
}
