package consulting.jjs.bestpricebe.filter;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import consulting.jjs.bestpricebe.exception.ResourceAlreadyExistsException;
import org.jboss.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ResourceAlreadyExistsExceptionMapper implements ExceptionMapper<ResourceAlreadyExistsException> {

  private static final Logger LOG = Logger.getLogger(ResourceAlreadyExistsExceptionMapper.class);

  @Override
  public Response toResponse(ResourceAlreadyExistsException exception) {
    LOG.error("API failed with a resource already exists exception", exception);
    return Response.status(Response.Status.CONFLICT)
            .entity(new SimpleMessage(exception.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }
}
