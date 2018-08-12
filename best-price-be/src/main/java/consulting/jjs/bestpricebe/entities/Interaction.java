package consulting.jjs.bestpricebe.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "INTERACTION")
public class Interaction implements Serializable {

  public static enum InteractionType {
    PHONE, EMAIL, SOCIAL_NETWORK, MEETING
  }

  @Id
  @SequenceGenerator(name="interaction_id_seq",
          sequenceName="interaction_id_seq",
          allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
          generator="interaction_id_seq")
  @Column(name = "ID", updatable=false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "CAMPAIGN_ID")
  @Getter
  @Setter
  @NotNull
  private Campaign campaign;

  @Column(name = "NOTES")
  @Getter
  @Setter
  private String notes;

  @Column(name = "PRICE")
  @Getter
  @Setter
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "CONTACT_ID")
  @Getter
  @Setter
  //@NotNull
  private Contact contact;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  @Getter
  @Setter
  private InteractionType type;

  @Column(name = "date")
  @Getter
  @Setter
  private Date date;

}
