package consulting.jjs.bestpricebe.dto;

import consulting.jjs.bestpricebe.entities.Interaction.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteractionDto {

  private String notes;
  private BigDecimal price;
  private Date date;
  private InteractionType type;
  private Integer campaignId;
  private Integer contactId;

}
