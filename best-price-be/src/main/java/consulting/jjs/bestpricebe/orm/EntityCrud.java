package consulting.jjs.bestpricebe.orm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

public class EntityCrud<E, I> {

  @PersistenceContext(unitName = "best-price-pu", type = PersistenceContextType.EXTENDED)
  protected EntityManager entityManager;

  private final Class<E> entityClass;

  EntityCrud(Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  public void create(E entity) {
    entityManager.persist(entity);
  }

  public void save(E entity) {
    entityManager.merge(entity);
  }

  public E getById(I id) {
    return entityManager.find(entityClass, id);
  }

  public void delete(E entity) {
    entityManager.remove(entity);
  }

  public List<E> getList() throws Exception {
    TypedQuery<E> query = entityManager.createQuery("SELECT o from " + entityClass.getSimpleName() + " o", entityClass);
    return query.getResultList();
  }

}
