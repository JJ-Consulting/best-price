package consulting.jjs.bestpricebe.filter;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import consulting.jjs.bestpricebe.exception.UnauthorizedException;
import org.jboss.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

  private static final Logger LOG = Logger.getLogger(UnauthorizedExceptionMapper.class);

  @Override
  public Response toResponse(UnauthorizedException exception) {
    LOG.error("API failed with a unauthorized exception", exception);
    return Response.status(Response.Status.UNAUTHORIZED)
            .entity(new SimpleMessage(exception.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }
}
