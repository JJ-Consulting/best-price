package consulting.jjs.bestpricebe.resources;

import consulting.jjs.bestpricebe.dto.InteractionDto;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.services.InteractionService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/interactions")
public class InteractionResource {

  @Inject
  private InteractionService interactionService;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createInteraction(InteractionDto interaction) throws TechnicalException {
    interactionService.create(interaction);
  }

  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public InteractionDto getInteraction(@PathParam("id") Integer interactionId) throws TechnicalException {
    return interactionService.getInteractionById(interactionId);
  }

}
