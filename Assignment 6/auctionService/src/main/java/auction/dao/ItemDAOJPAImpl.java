package auction.dao;

import auction.domain.Item;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ItemDAOJPAImpl implements ItemDAO {

    private EntityManager em;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * @return number of item instances
     */
    @Override
    public int count() {
        Query query = em.createNamedQuery("Item.count", Item.class);
        return ((Long)query.getSingleResult()).intValue();
    }

    /**
     * The item is persisted. If a item with the same id allready exists an EntityExistsException is thrown
     *
     * @param item
     */
    @Override
    public void create(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    /**
     * Merge the state of the given item into persistant context. If the item did not exist an IllegalArgumentException is thrown
     *
     * @param item
     */
    @Override
    public void edit(Item item) {
        em.merge(item);
    }

    /**
     * @param id
     * @return the found entity instance or null if the entity does not exist
     */
    @Override
    public Item find(Long id) throws NoResultException {
        Query query = em.createNamedQuery("Item.findById", Item.class);
        query.setParameter("id", id);

        Item item;

        try {
            item = (Item) query.getSingleResult();
        }catch (NoResultException e){
            item = null;
        }
        return item;
    }

    /**
     * @return list of item instances
     */
    @Override
    public List<Item> findAll() throws NoResultException {
        return em.createNamedQuery("Item.getAll", Item.class).getResultList();
    }

    /**
     * @param description
     * @return list of item instances having specified description
     */
    @Override
    public List<Item> findByDescription(String description) throws NoResultException {
        Query query = em.createNamedQuery("Item.findByDescription", Item.class);
        query.setParameter("description", description);

        return (List<Item>) query.getResultList();
    }

    /**
     * Remove the entity instance
     *
     * @param item - entity instance
     */
    @Override
    public void remove(Item item) {
        em.getTransaction().begin();
        em.remove(item);
        em.getTransaction().commit();
    }
}
