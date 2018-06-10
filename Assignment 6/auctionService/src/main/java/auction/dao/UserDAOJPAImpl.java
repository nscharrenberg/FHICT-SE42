package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    private EntityManager em;

    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query query = em.createNamedQuery("User.count", User.class);
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }

        em.merge(user);
    }


    @Override
    public List<User> findAll() {
        return em.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);

        try {
            return (User) query.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public void remove(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}
