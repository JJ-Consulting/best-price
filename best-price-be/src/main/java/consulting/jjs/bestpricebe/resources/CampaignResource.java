package consulting.jjs.bestpricebe.resources;

import consulting.jjs.bestpricebe.dto.CampaignDto;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.filter.JWTTokenNeeded;
import consulting.jjs.bestpricebe.filter.Logged;
import consulting.jjs.bestpricebe.services.CampaignService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/campaigns")
@Logged
public class CampaignResource {

  @Inject
  private CampaignService campaignService;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @JWTTokenNeeded
  public void createCampaign(CampaignDto campaignDto) {
    campaignService.createCampaign(campaignDto);
  }

  @Path("/{id}")
  @DELETE
  public void deleteCampaign(@PathParam("id") Integer campaignId) throws TechnicalException {
    campaignService.deleteCampaign(campaignId);
  }

  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @JWTTokenNeeded
  public CampaignDto getCampaign(@PathParam("id") Integer campaignId) throws TechnicalException {
    return campaignService.getCampaign(campaignId);
  }

}
