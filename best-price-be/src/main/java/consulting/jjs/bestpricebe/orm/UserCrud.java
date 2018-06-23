package consulting.jjs.bestpricebe.orm;

import consulting.jjs.bestpricebe.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class UserCrud extends EntityCrud<User, Integer> {
  UserCrud() {
    super(User.class);
  }

  public User getUserByLoginAndPassword(String login, String password) throws NoResultException {
    return entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login and u.password = :password", User.class)
            .setParameter("login", login)
            .setParameter("password", password)
            .getSingleResult();
  }

  public User getUserByEmailAndPassword(String email, String password) throws NoResultException {
    return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email and u.password = :password", User.class)
            .setParameter("email", email)
            .setParameter("password", password)
            .getSingleResult();
  }

  public User getUserByEmail(String email) throws NoResultException {
    return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .getSingleResult();
  }

  public List<User> getUsersByEmailOrLogin(String email, String login) {
    return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email OR u.login = :login", User.class)
            .setParameter("email", email)
            .setParameter("login", login)
            .getResultList();
  }

}
