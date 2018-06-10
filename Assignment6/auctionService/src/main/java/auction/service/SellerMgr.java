package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

public class SellerMgr {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auction");
    private EntityManager em = emf.createEntityManager();
    private ItemDAO itemDao;

    public SellerMgr() {
        this.itemDao = new ItemDAOJPAImpl(em);
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if(item.getHighestBid() != null) {
            return false;
        }

        itemDao.remove(item);
        return true;
    }

    public Item offerItem(User seller, Category category, String description) {
        Item item = new Item(seller, category, description);
        itemDao.create(item);

        return item;
    }
    
    public Category addCategory(String description) {
        return new Category(description);
    }
    
    public Money addMoney(long cents, String currency) {
        return new Money(cents, currency);
    }
}
