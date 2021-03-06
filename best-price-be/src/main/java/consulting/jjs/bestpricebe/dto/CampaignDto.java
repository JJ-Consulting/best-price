package consulting.jjs.bestpricebe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto {

  private Integer              id;
  private String               name;
  private String               currency;
  private List<InteractionDto> interactions;
  private Date                 startDate;
  private Date                 endDate;

}
