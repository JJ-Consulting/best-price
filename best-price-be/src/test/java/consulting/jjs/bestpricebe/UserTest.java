package consulting.jjs.bestpricebe;

import consulting.jjs.bestpricebe.dto.UserDto;
import consulting.jjs.bestpricebe.exception.FunctionalException;
import consulting.jjs.bestpricebe.services.UserService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class UserTest extends AbstractInContainerTest {

  @Inject
  private UserService userService;

  @Test
  public void getUser() throws FunctionalException {
    // TODO
    userService.createUser(UserDto.builder().email("foo@mail.com").login("foo").password("bar").build());
  }

}
