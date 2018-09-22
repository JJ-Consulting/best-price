package consulting.jjs.bestpricebe;

import consulting.jjs.bestpricebe.dto.SimpleMessage;
import consulting.jjs.bestpricebe.dto.UserDto;
import consulting.jjs.bestpricebe.entities.User;
import consulting.jjs.bestpricebe.exception.UnauthorizedException;
import consulting.jjs.bestpricebe.resources.UserResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class UserTest extends AbstractInContainerTest {

  @Inject
  private UserResource  userResource;

  @Test
  public void shouldInsertUserInDB() throws Exception {
    // GIVEN
    UserDto userDto = UserDto.builder()
            .email("foobar@mail.com")
            .login("foobar")
            .password("baz")
            .build();

    // WHEN
    transaction.begin();
    userResource.create(userDto);
    transaction.commit();
    em.clear();

    // THEN
    User user = em.createQuery("SELECT u from User u where u.login = 'foobar'", User.class).getSingleResult();
    assertEquals("User email should be the same", userDto.getEmail(), user.getEmail());
    assertEquals("User login should be the same", userDto.getLogin(), user.getLogin());
    assertEquals("User password should be the same", userDto.getPassword(), user.getPassword());
  }

  @Test
  public void shouldLoginUser() throws Exception {
    // GIVEN
    User user = new User();
    user.setEmail("foobar2@mail.com");
    user.setLogin("foobar2");
    user.setPassword("baz2");
    transaction.begin();
    em.persist(user);
    transaction.commit();
    em.clear();

    // WHEN
    SimpleMessage result = userResource.authenticateUser("foobar2@mail.com", "baz2");

    // THEN
    assertNotNull("Authentication should return a token", result.getMessage());

    // WHEN
    result = userResource.authenticateUser("foobar2", "baz2");

    // THEN
    assertNotNull("Authentication should return a token", result.getMessage());
  }

  @Test(expected = UnauthorizedException.class) // THEN
  public void shouldFailWhileLoginUser() throws UnauthorizedException {
    // WHEN
    userResource.authenticateUser("foobaz", "baz");
  }

}
