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
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
//        Item newItem = new Item(seller, cat, description);
//        itemDao.create(newItem);
//        seller.addItemToSeller(newItem);
        return null;
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
        seller.addItemToSeller(furniture);

        return furniture;
    }

    public Painting offerPainting(User seller, Category category, String description, String title, String painter) {
        Painting painting = new Painting(seller, category, description, title, painter);
        itemDao.create(painting);
        seller.addItemToSeller(painting);

        return painting;
    }
}
