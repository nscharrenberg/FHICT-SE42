package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public Furniture offerFurniture(User seller, Category category, String description, String material) {
        Furniture furniture = new Furniture(seller, category, description, material);
        itemDao.create(furniture);

        return furniture;
    }

    public Painting offerPainting(User seller, Category category, String description, String title, String painter) {
        Painting painting = new Painting(seller, category, description, title, painter);
        itemDao.create(painting);

        return painting;
    }
}
