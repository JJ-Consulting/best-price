package consulting.jjs.bestpricebe.services;

import consulting.jjs.bestpricebe.dto.UserDto;
import consulting.jjs.bestpricebe.entities.User;
import consulting.jjs.bestpricebe.exception.FunctionalException;
import consulting.jjs.bestpricebe.exception.ResourceAlreadyExistsException;
import consulting.jjs.bestpricebe.exception.UnauthorizedException;
import consulting.jjs.bestpricebe.orm.UserCrud;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@RequestScoped
public class UserService {

  @Inject
  private UserCrud userOrm;

  @Setter
  @Getter
  private String currentUserEmail;

  @Transactional
  public void createUser(UserDto userDto) throws FunctionalException {
    checkIfUserExist(userDto);
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setLogin(userDto.getLogin());
    user.setPassword(userDto.getPassword());

    userOrm.create(user);
  }

  private void checkIfUserExist(UserDto userDto) throws FunctionalException {
    if (!userOrm.getUsersByEmailOrLogin(userDto.getEmail(), userDto.getLogin()).isEmpty()) {
      throw new ResourceAlreadyExistsException("User already exists");
    }
  }

  public UserDto authenticate(String login, String password) throws UnauthorizedException {
    try {
      User result;
      if (login.contains("@")) {
        result = userOrm.getUserByEmailAndPassword(login, password);
      } else {
        result = userOrm.getUserByLoginAndPassword(login, password);
      }
      return toDto(result);
    } catch(NoResultException e) {
      throw new UnauthorizedException("Authentication failed with given login and password.");
    }
  }

  private UserDto toDto(User result) {
    return UserDto.builder()
            .email(result.getEmail())
            .login(result.getLogin())
            .build();
  }

  public User getCurrentUser() {
    if (currentUserEmail == null) {
      throw new IllegalStateException("currentUserEmail cannot be null");
    }
    return userOrm.getUserByEmail(currentUserEmail);
  }
}
