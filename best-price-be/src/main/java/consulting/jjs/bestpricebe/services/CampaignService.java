package consulting.jjs.bestpricebe.services;

import consulting.jjs.bestpricebe.dto.CampaignDto;
import consulting.jjs.bestpricebe.entities.Campaign;
import consulting.jjs.bestpricebe.entities.User;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.orm.CampaignCrud;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class CampaignService {

  private static Logger LOG = Logger.getLogger(CampaignService.class);

  @Inject
  private CampaignCrud campaignOrm;

  @Inject
  private InteractionService interactionService;

  @Inject
  private UserService userService;

  @Transactional
  public void createCampaign(CampaignDto campaignDto) {
    User currentUser = userService.getCurrentUser();
    Campaign campaign = new Campaign();
    campaign.setName(campaignDto.getName());
    campaign.setCurrency(campaignDto.getCurrency());
    campaign.setStartDate(campaignDto.getStartDate());
    campaign.setEndDate(campaignDto.getEndDate());
    currentUser.addCampaign(campaign);

    validateCampaign(campaign);
    campaignOrm.create(campaign);
  }

  /**
   * {@link javax.persistence.EntityManager#persist(Object)} doesn't throw Exception because
   * of the way the entity manager lifecycle is managed. The validation exception is thrown
   * by this method.
   *
   * @param campaign the campaign to validate
   */
  private void validateCampaign(Campaign campaign) {
    Set<ConstraintViolation<Campaign>> constraints = Validation.buildDefaultValidatorFactory()
            .getValidator()
            .validate(campaign);
    if (!constraints.isEmpty()) {
      throw new ConstraintViolationException(constraints);
    }
  }

  @Transactional
  public void deleteCampaign(Integer campaignId) throws TechnicalException {
    campaignOrm.delete(findCampaignOrThrowException(campaignId));
  }

  @Transactional
  public CampaignDto getCampaign(Integer campaignId) throws TechnicalException {
    return toDto(findCampaignOrThrowException(campaignId));
  }

  @Transactional
  public List<CampaignDto> getAllCampaigns() {
    return campaignOrm.getByUserEmail(userService.getCurrentUserEmail())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
  }

  private CampaignDto toDto(Campaign campaign) {
    return CampaignDto.builder()
            .id(campaign.getId())
            .name(campaign.getName())
            .currency(campaign.getCurrency())
            .startDate(campaign.getStartDate())
            .endDate(campaign.getEndDate())
            .interactions(campaign.getInteractions().stream()
                    .map(interactionService::toDto)
                    .collect(Collectors.toList()))
            .build();
  }

  public Campaign findCampaignOrThrowException(Integer campaignId) throws TechnicalException {
    if (campaignId == null) {
      throw new IllegalArgumentException("campaignId cannot be null");
    }

    return campaignOrm.getByIdAndUserEmail(campaignId, userService.getCurrentUserEmail());
  }

}
