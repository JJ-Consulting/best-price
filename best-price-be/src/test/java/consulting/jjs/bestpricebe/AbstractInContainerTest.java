package consulting.jjs.bestpricebe;

import io.jsonwebtoken.Jwt;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;

public abstract class AbstractInContainerTest {

  @PersistenceContext(unitName = "best-price-pu", type = PersistenceContextType.EXTENDED)
  protected EntityManager em;

  @Inject
  protected UserTransaction transaction;

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class, "tests.war")
            .addPackages(true, AbstractInContainerTest.class.getPackage())
            .addPackages(true, Jwt.class.getPackage())
            .addAsResource("project-testing.yml", "project-defaults.yml")
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

}
