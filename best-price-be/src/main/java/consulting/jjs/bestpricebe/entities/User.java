package consulting.jjs.bestpricebe.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "BP_USER")
@NamedQuery(name = "User.getByEmail", query = "SELECT u FROM User u where u.email = :email")
public class User implements Serializable {

  @Id
  @SequenceGenerator(name = "bp_user_id_seq",
          sequenceName = "bp_user_id_seq",
          allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
          generator = "bp_user_id_seq")
  @Column(name = "ID", updatable = false)
  @Getter
  private Integer id;

  @Column(name = "LOGIN", unique = true)
  @Getter
  @Setter
  @NotNull
  private String login;

  @Column(name = "EMAIL", unique = true)
  @Getter
  @Setter
  @NotNull
  private String email;

  @Column(name = "PASSWORD")
  @Getter
  @Setter
  @NotNull
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @Getter
  private List<Campaign> campaigns;

  public void addCampaign(Campaign campaign) {
    campaign.setUser(this);
    campaigns.add(campaign);
  }


}
