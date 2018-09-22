package consulting.jjs.bestpricebe.resources;

import consulting.jjs.bestpricebe.dto.CampaignDto;
import consulting.jjs.bestpricebe.exception.TechnicalException;
import consulting.jjs.bestpricebe.filter.JWTTokenNeeded;
import consulting.jjs.bestpricebe.filter.Logged;
import consulting.jjs.bestpricebe.services.CampaignService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/campaigns")
@Logged
@JWTTokenNeeded
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CampaignResource {

  private static Logger LOG = Logger.getLogger(CampaignResource.class);

  @Inject
  private CampaignService campaignService;

  @POST
  public Response createCampaign(CampaignDto campaignDto) {
    campaignService.createCampaign(campaignDto);
    return Response.status(Response.Status.CREATED).build();
  }

  @GET
  public List<CampaignDto> getCampaigns() {
    return campaignService.getAllCampaigns();
  }

  @Path("/{id}")
  @DELETE
  public void deleteCampaign(@PathParam("id") Integer campaignId) throws TechnicalException {
    campaignService.deleteCampaign(campaignId);
  }

  @Path("/last")
  @GET
  public CampaignDto getLatestCampaign() throws TechnicalException {
    return campaignService.getLastCampaign();
  }

  @Path("/{id}")
  @GET
  public CampaignDto getCampaign(@PathParam("id") Integer campaignId) throws TechnicalException {
    return campaignService.getCampaign(campaignId);
  }

}
