package consulting.jjs.bestpricebe.orm;

import consulting.jjs.bestpricebe.entities.Interaction;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InteractionCrud extends EntityCrud<Interaction, Integer> {
  public InteractionCrud() {
    super(Interaction.class);
  }
}
