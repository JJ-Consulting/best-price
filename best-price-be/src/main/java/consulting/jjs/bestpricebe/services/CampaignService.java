package consulting.jjs.bestpricebe.services;

import consulting.jjs.bestpricebe.dto.CampaignDto;
import consulting.jjs.bestpricebe.entities.Campaign;
import consulting.jjs.bestpricebe.entities.User;
import consulting.jjs.bestpricebe.exception.ResourceNotFoundException;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.orm.CampaignCrud;
import consulting.jjs.bestpricebe.resources.HelloWorldEndpoint;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class CampaignService {

  private static Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

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
    currentUser.addCampaign(campaign);

    campaignOrm.create(campaign);
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
            .name(campaign.getName())
            .currency(campaign.getCurrency())
            .interactions(campaign.getInteractions().stream()
                    .map(interactionService::toDto)
                    .collect(Collectors.toList()))
            .build();
  }

  public Campaign findCampaignOrThrowException(Integer campaignId) throws TechnicalException {
    Campaign campaign;
    if (campaignId == null || ((campaign = campaignOrm.getByIdAndUserEmail(campaignId, userService.getCurrentUserEmail())) == null)) {
      throw new ResourceNotFoundException("Unable to find campaign with id " + campaignId);
    }
    return campaign;
  }

}
