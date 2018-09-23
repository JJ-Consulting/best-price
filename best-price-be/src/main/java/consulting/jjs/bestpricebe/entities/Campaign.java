package consulting.jjs.bestpricebe.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CAMPAIGN")
public class Campaign implements Serializable {

  @Id
  @SequenceGenerator(name = "campaign_id_seq",
          sequenceName = "campaign_id_seq",
          allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
          generator = "campaign_id_seq")
  @Column(name = "ID", updatable = false)
  @Getter
  private Integer id;

  @Column(name = "NAME")
  @Getter
  @Setter
  @NotNull
  private String name;

  @Column(name = "CURRENCY", length = 3)
  @Getter
  @Setter
  private String currency;

  @Column(name = "START_DATE", nullable = false)
  @Getter
  @Setter
  @Past
  private Date startDate;

  @Column(name = "END_DATE")
  @Getter
  @Setter
  @Past
  private Date endDate;

  @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
  @Getter
  public List<Interaction> interactions = new ArrayList<>();

  @ManyToMany(cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
  }, fetch = FetchType.LAZY)
  @JoinTable(name = "campaign_contact",
          joinColumns = @JoinColumn(name = "campaign_id"),
          inverseJoinColumns = @JoinColumn(name = "contact_id")
  )
  @Getter
  public Set<Contact> contacts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  @Getter
  @Setter
  @NotNull
  private User user;

  // TODO: handle deleted
  @Column(name = "DELETED")
  private Integer deleted;

  public void addInteraction(Interaction interaction) {
    interactions.add(interaction);
    interaction.setCampaign(this);
  }

  public void addContact(Contact contact) {
    contacts.add(contact);
    contact.getCampaigns().add(this);
  }
}
