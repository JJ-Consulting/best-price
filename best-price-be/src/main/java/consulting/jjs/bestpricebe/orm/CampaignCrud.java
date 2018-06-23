package consulting.jjs.bestpricebe.orm;

import consulting.jjs.bestpricebe.entities.Campaign;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class CampaignCrud extends EntityCrud<Campaign, Integer> {


  private static final String GET_BY_USER_QUERY = "SELECT c FROM Campaign c INNER JOIN c.user u WHERE u.email = :email";
  private static final String GET_BY_ID_AND_USER_QUERY = GET_BY_USER_QUERY + " AND c.id = :id";

  public CampaignCrud() {
    super(Campaign.class);
  }

  public Campaign getByIdAndUserEmail(Integer id, String email) {
    return entityManager.createQuery(GET_BY_ID_AND_USER_QUERY, Campaign.class)
            .setParameter("id", id)
            .setParameter("email", email)
            .getSingleResult();
  }

  public List<Campaign> getByUserEmail(String email) {
    return entityManager.createQuery(GET_BY_USER_QUERY, Campaign.class)
            .setParameter("email", email)
            .getResultList();
  }

}
