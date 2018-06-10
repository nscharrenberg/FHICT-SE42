/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.webService;

import auction.domain.*;
import auction.service.*;
import java.util.List;
import javax.jws.WebService;
import nl.fontys.util.*;

/**
 *
 * @author Noah Scharrenberg
 */
@WebService
public class Auction {
    private AuctionMgr auctionMgr;
    private SellerMgr sellerMgr;

    public Auction() {
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
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
    
    public Item offerPainting(User seller, Category category, String description, String title, String painter) {
        return sellerMgr.offerPainting(seller, category, description, title, painter);
    }
    
    public Item offerFurniture(User seller, Category category, String description, String material) {
        return sellerMgr.offerFurniture(seller, category, description, material);
    }
    
    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }
}
