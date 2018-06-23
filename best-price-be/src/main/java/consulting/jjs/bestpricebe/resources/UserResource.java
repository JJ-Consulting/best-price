package consulting.jjs.bestpricebe.resources;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import consulting.jjs.bestpricebe.dto.UserDto;
import consulting.jjs.bestpricebe.exception.FunctionalException;
import consulting.jjs.bestpricebe.exception.UnauthorizedException;
import consulting.jjs.bestpricebe.services.KeyService;
import consulting.jjs.bestpricebe.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Path("/users")
public class UserResource {

  @Context
  private UriInfo uriInfo;

  @Inject
  private UserService userService;

  @Inject
  private KeyService keyService;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void create(UserDto user) throws FunctionalException {
    if (user.getLogin() == null) {
      user.setLogin(user.getEmail());
    }
    userService.createUser(user);
  }

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public SimpleMessage authenticateUser(@FormParam("login") String login,
                                        @FormParam("password") String password) throws UnauthorizedException {
    if (login == null || password == null) {
      throw new BadRequestException();
    }
    return authenticate(login, password);
  }

  private SimpleMessage authenticate(@FormParam("login") String login, @FormParam("password") String password) throws UnauthorizedException {
    UserDto user = userService.authenticate(login, password);
    return new SimpleMessage("Bearer " + issueToken(user.getEmail()));
  }

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public SimpleMessage authenticateUser(UserDto user) throws UnauthorizedException {
    return authenticate(user.getLogin(), user.getPassword());
  }

  private String issueToken(String login) {
    return Jwts.builder()
            .setSubject(login)
            .setIssuer("best-price")
            .setIssuedAt(new Date())
            .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
            .signWith(SignatureAlgorithm.HS512, keyService.getSecretKey())
            .compact();
  }

  private Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }



}
