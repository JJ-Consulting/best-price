package consulting.jjs.bestpricebe;

import consulting.jjs.bestpricebe.filter.ConstraintViolationExceptionMapper;
import consulting.jjs.bestpricebe.filter.DefaultExceptionMapper;
import consulting.jjs.bestpricebe.filter.JWTTokenNeededFilter;
import consulting.jjs.bestpricebe.filter.RequestLoggingFilter;
import consulting.jjs.bestpricebe.filter.ResourceAlreadyExistsExceptionMapper;
import consulting.jjs.bestpricebe.filter.ResourceNotFoundMapper;
import consulting.jjs.bestpricebe.filter.UnauthorizedExceptionMapper;
import consulting.jjs.bestpricebe.resources.CampaignResource;
import consulting.jjs.bestpricebe.resources.InteractionResource;
import consulting.jjs.bestpricebe.resources.UserResource;

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
            CampaignResource.class,
            InteractionResource.class,
            UserResource.class,

            // Exceptions mapper
            DefaultExceptionMapper.class,
            ResourceNotFoundMapper.class,
            UnauthorizedExceptionMapper.class,
            ResourceAlreadyExistsExceptionMapper.class,
            ConstraintViolationExceptionMapper.class,

            // Filters
            RequestLoggingFilter.class,
            JWTTokenNeededFilter.class));
  }

}