/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.webService;

import auction.domain.*;
import auction.service.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.*;
import util.DatabaseCleaner;

/**
 *
 * @author Noah Scharrenberg
 */
@WebService
public class Auction {
    private AuctionMgr auctionMgr;
    private SellerMgr sellerMgr;
    private util.DatabaseCleaner dbCleaner;
    private EntityManager em;
    private EntityManagerFactory factory;

    public Auction() {
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        factory = Persistence.createEntityManagerFactory("auction");
        em = factory.createEntityManager();
    }

    public Item getItem(Long id) {
        return auctionMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description) {
        return auctionMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount) {
        return auctionMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category category, String description) {
        return sellerMgr.offerItem(seller, category, description);
    }
    
    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }
    
    public Category addCategory(String description) {
        return sellerMgr.addCategory(description);
    }
    
    public Money addMoney(long cents, String currency) {
        return sellerMgr.addMoney(cents, currency);
    }
    
    public void cleanDatabase() {
        try {
            dbCleaner = new DatabaseCleaner(em);
            dbCleaner.clean();
        } catch (SQLException ex) {
            Logger.getLogger(Auction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
