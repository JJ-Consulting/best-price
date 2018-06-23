package consulting.jjs.bestpricebe.orm;

import consulting.jjs.bestpricebe.entities.Contact;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContactCrud extends EntityCrud<Contact, Integer> {

  ContactCrud() {
    super(Contact.class);
  }

}
