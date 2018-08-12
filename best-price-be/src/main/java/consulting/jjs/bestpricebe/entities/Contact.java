package consulting.jjs.bestpricebe.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CONTACT")
public class Contact implements Serializable {

  @Id
  @SequenceGenerator(name="contact_id_seq",
          sequenceName="contact_id_seq",
          allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
          generator="contact_id_seq")
  @Column(name = "ID", updatable=false)
  private Integer id;

  @Column(name = "FIRST_NAME")
  @Getter
  @Setter
  private String firstName;

  @Column(name = "LAST_NAME")
  @Getter
  @Setter
  private String lastName;

  @Column(name = "COMPANY")
  @Getter
  @Setter
  private String company;

  @Column(name = "ROLE")
  @Getter
  @Setter
  private String role;

  @Column(name = "WORK_PHONE")
  @Getter
  @Setter
  private String workPhone;

  @Column(name = "MOBILE_PHONE")
  @Getter
  @Setter
  private String mobilePhone;

  @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  @Getter
  private List<Interaction> interactions;

  public void addInteraction(Interaction interaction) {
    interactions.add(interaction);
    interaction.setContact(this);
  }

  @ManyToMany(mappedBy = "contacts")
  @Getter
  public Set<Campaign> campaigns;

  @Column(name = "STARTING_DATE")
  @Getter
  @Setter
  public Date startingDate;

  @Column(name = "ENDING_DATE")
  @Getter
  @Setter
  public Date endingDate;

}
