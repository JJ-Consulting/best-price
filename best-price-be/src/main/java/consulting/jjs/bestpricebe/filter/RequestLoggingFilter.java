package consulting.jjs.bestpricebe.filter;


import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Logged
@Provider
public class RequestLoggingFilter implements ContainerResponseFilter {

  private static final Logger LOG = Logger.getLogger(RequestLoggingFilter.class);
  private static final String REQUEST_LOG_FORMAT = "METHOD: %s; PATH: %s; RESPONSE: %d";

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    LOG.infof(REQUEST_LOG_FORMAT,
            requestContext.getMethod(),
            requestContext.getUriInfo().getPath(),
            responseContext.getStatus());
  }
}
