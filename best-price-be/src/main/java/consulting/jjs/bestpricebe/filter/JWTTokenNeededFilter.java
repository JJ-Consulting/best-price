package consulting.jjs.bestpricebe.filter;

import consulting.jjs.bestpricebe.services.KeyService;
import consulting.jjs.bestpricebe.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.jboss.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

  private static final Logger LOG = Logger.getLogger(JWTTokenNeededFilter.class);

  @Inject
  private KeyService keyService;

  @Inject
  private UserService userService;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      LOG.error("#### invalid authorizationHeader : " + authorizationHeader);
      throw new NotAuthorizedException("Authorization header must be provided");
    }

    // Extract the token from the HTTP Authorization header
    String token = authorizationHeader.substring("Bearer".length()).trim();

    try {
      // Validate the token
      Jws<Claims> claims = Jwts.parser()
              .setSigningKey(keyService.getSecretKey())
              .parseClaimsJws(token);
      userService.setCurrentUserEmail(claims.getBody().getSubject());
    } catch (Exception e) {
      LOG.error("#### invalid token : " + token);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }
}