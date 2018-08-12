package consulting.jjs.bestpricebe.filter;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import org.jboss.logging.Logger;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  private Logger LOG = Logger.getLogger(ConstraintViolationExceptionMapper.class);

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    String validationExceptionMessge = exception.getConstraintViolations()
            .stream()
            .map(c -> c.getPropertyPath() + " " + c.getMessage())
            .reduce("Entity is not valid", (a, b) -> a + "; " + b);
    LOG.error(validationExceptionMessge, exception);
    return Response.status(Response.Status.BAD_REQUEST)
            .entity(new SimpleMessage(validationExceptionMessge))
            .build();
  }
}
