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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CampaignTest extends AbstractInContainerTest {

  @PersistenceContext(unitName = "best-price-pu", type = PersistenceContextType.EXTENDED)
  private EntityManager em;

  @Inject
  private UserTransaction transaction;

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
  public void shouldCreateCampaign() {
    // GIVEN
    CampaignDto campaignDto = CampaignDto.builder()
            .name("foo")
            .currency("USD")
            .startDate(Date.from(Instant.parse("2018-07-05T15:30:30Z")))
            .endDate(Date.from(Instant.parse("2018-08-10T15:30:30Z")))
            .build();


    // WHEN
    campaignResource.createCampaign(campaignDto);

    // THEN
    em.clear();
    Campaign campaign = em.createQuery("select c from Campaign c where c.name = 'foo'", Campaign.class).getSingleResult();
    assertEquals(campaignDto.getName(), campaign.getName());
    assertEquals(campaignDto.getCurrency(), campaign.getCurrency());
    assertEquals(campaignDto.getStartDate(), campaign.getStartDate());
    assertEquals(campaignDto.getEndDate(), campaign.getEndDate());
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

}
