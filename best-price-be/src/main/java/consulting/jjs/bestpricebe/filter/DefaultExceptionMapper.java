package consulting.jjs.bestpricebe.filter;

import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOG = Logger.getLogger(DefaultExceptionMapper.class);

  @Override
  public Response toResponse(Exception exception) {
    LOG.error("Unhandled exception thrown by API", exception);
    return Response.status(Status.SERVICE_UNAVAILABLE).build();
  }
}
