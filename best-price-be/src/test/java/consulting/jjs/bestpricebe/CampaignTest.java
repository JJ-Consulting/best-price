package consulting.jjs.bestpricebe;

import consulting.jjs.bestpricebe.dto.CampaignDto;
import consulting.jjs.bestpricebe.entities.Campaign;
import consulting.jjs.bestpricebe.entities.User;
import consulting.jjs.bestpricebe.resources.CampaignResource;
import consulting.jjs.bestpricebe.services.UserService;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.spi.ArquillianProxyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class CampaignTest extends AbstractInContainerTest {

  private static boolean initialized = false;

  @Inject
  private CampaignResource campaignResource;

  @Inject
  private UserService userService;

  @Before
  public void initContext() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
    // user retrieved from JWT
    userService.setCurrentUserEmail("foo@mail.com");

    if (!initialized) {
      initialized = true;

      // the user should exist in DB
      transaction.begin();
      User user = new User();
      user.setLogin("foo");
      user.setPassword("bar");
      user.setEmail("foo@mail.com");
      em.persist(user);
      transaction.commit();
      em.clear();
    }
  }

  @Test
  public void shouldInsertCampaignInDB() throws Exception {
    // GIVEN
    transaction.begin();
    CampaignDto campaignDto = CampaignDto.builder()
            .name("foo")
            .currency("USD")
            .startDate(Date.from(Instant.parse("2018-07-05T15:30:30Z")))
            .build();

    // WHEN
    CampaignDto campaignResult = campaignResource.createCampaign(campaignDto);
    transaction.commit();

    // THEN
    em.clear();
    assertNotNull("it should return newly created campaign id", campaignResult.getId());
    Campaign campaign = em.createQuery("select c from Campaign c where c.name = 'foo'", Campaign.class).getSingleResult();
    assertEquals(campaignDto.getName(), campaign.getName());
    assertEquals(campaignDto.getCurrency(), campaign.getCurrency());
    assertEquals(campaignDto.getStartDate(), campaign.getStartDate());
  }

  /**
   * {@code ConstraintViolationException} will be proxied to {@code ArquillianProxyException}
   * because of exception serialisation issue on client side: {@code ConstraintViolationImpl} not found by Arquillian
   * client
   *
   * @throws ConstraintViolationException
   */
  @Test(expected = ArquillianProxyException.class) // THEN
  public void shouldFailWithBadRequest() {
    // GIVEN
    CampaignDto campaignDto = CampaignDto.builder()
            .currency("USD")
            .startDate(Date.from(Instant.parse("2018-07-05T15:30:30Z")))
            .endDate(Date.from(Instant.parse("2018-08-10T15:30:30Z")))
            .build();

    // WHEN
    campaignResource.createCampaign(campaignDto);
  }

  @Test
  public void getLatestCampaign() throws Exception {
    transaction.begin();
    User user = em.createQuery("SELECT u from User u where u.login = 'foo'", User.class).getSingleResult();
    Campaign c1 = new Campaign();
    c1.setName("a");
    c1.setCurrency("USD");
    c1.setStartDate(Date.from(Instant.parse("2018-09-20T12:58:32.00Z")));
    c1.setUser(user);
    em.persist(c1);

    Campaign c2 = new Campaign();
    c2.setName("b");
    c2.setCurrency("USD");
    c2.setStartDate(Date.from(Instant.parse("2018-09-22T12:58:32.00Z")));
    c2.setUser(user);
    em.persist(c2);

    Campaign c3 = new Campaign();
    c3.setName("c");
    c3.setCurrency("USD");
    c3.setStartDate(Date.from(Instant.parse("2018-09-18T12:58:32.00Z")));
    c3.setUser(user);
    em.persist(c3);
    transaction.commit();

    // WHEN
    CampaignDto latestCampaign = campaignResource.getLatestCampaign();

    // THEN
    assertEquals("Latest campaign should be c2", "b", latestCampaign.getName());
  }

}
