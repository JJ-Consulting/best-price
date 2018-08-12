package consulting.jjs.bestpricebe;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractInContainerTest {

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class, "tests.war")
            .addPackages(true, AbstractInContainerTest.class.getPackage())
            .addAsResource("project-testing.yml", "project-defaults.yml")
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

}
