package consulting.jjs.bestpricebe.services;

import consulting.jjs.bestpricebe.dto.InteractionDto;
import consulting.jjs.bestpricebe.entities.Campaign;
import consulting.jjs.bestpricebe.entities.Interaction;
import consulting.jjs.bestpricebe.exception.ResourceNotFoundException;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.orm.InteractionCrud;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@RequestScoped
public class InteractionService {

  @Inject
  private InteractionCrud interactionOrm;

  @Inject CampaignService campaignService;

  @Transactional
  public void create(InteractionDto interactionDto) throws TechnicalException {
    Interaction interaction = new Interaction();

    Campaign campaign = campaignService.findCampaignOrThrowException(interactionDto.getCampaignId());
    // TODO: do the same with contactService

    campaign.addInteraction(interaction);

    interaction.setDate(interactionDto.getDate());
    interaction.setNotes(interactionDto.getNotes());
    interaction.setPrice(interactionDto.getPrice());
    interaction.setType(interactionDto.getType());

    interactionOrm.save(interaction);
  }

  public InteractionDto getInteractionById(Integer interactionId) throws TechnicalException {
    return toDto(findInteractionOrThrowException(interactionId));
  }

  public InteractionDto toDto(Interaction interaction) {
    return InteractionDto.builder()
            .notes(interaction.getNotes())
            .date(interaction.getDate())
            .price(interaction.getPrice())
            .type(interaction.getType())
            .campaignId(interaction.getCampaign().getId())
            .build();
  }

  public Interaction findInteractionOrThrowException(Integer interactionId) throws TechnicalException {
    Interaction interaction;
    if (interactionId == null || ((interaction = interactionOrm.getById(interactionId)) == null)) {
      throw new ResourceNotFoundException("Unable to find interaction with id " + interactionId);
    }
    return interaction;
  }
}
