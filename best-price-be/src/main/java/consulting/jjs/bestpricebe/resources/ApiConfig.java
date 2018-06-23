package consulting.jjs.bestpricebe.resources;

import consulting.jjs.bestpricebe.filter.DefaultExceptionMapper;
import consulting.jjs.bestpricebe.filter.JWTTokenNeededFilter;
import consulting.jjs.bestpricebe.filter.RequestLoggingFilter;
import consulting.jjs.bestpricebe.filter.ResourceAlreadyExistsExceptionMapper;
import consulting.jjs.bestpricebe.filter.ResourceNotFoundMapper;
import consulting.jjs.bestpricebe.filter.UnauthorizedExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApiConfig extends Application {

  public Set<Class<?>> getClasses() {
    return new HashSet<>(Arrays.asList(
            // End points
            HelloWorldEndpoint.class,
            CampaignResource.class,
            InteractionResource.class,
            UserResource.class,

            // Exceptions mapper
            DefaultExceptionMapper.class,
            ResourceNotFoundMapper.class,
            UnauthorizedExceptionMapper.class,
            ResourceAlreadyExistsExceptionMapper.class,

            // Filters
            RequestLoggingFilter.class,
            JWTTokenNeededFilter.class));
  }

}