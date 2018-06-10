/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.service;

import auction.webservice.AuctionService;
import auction.webservice.Bid;
import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.Money;
import auction.webservice.RegistrationService;
import auction.webservice.User;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Noah Scharrenberg
 */
public class AuctionMgrTest {
    
    public AuctionMgrTest() {
    }

    @Test
    public void getItem() {
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = WebLogic.registerUser(email);
        Category cat = WebLogic.addCategory("cat2");
        Item item1 = WebLogic.offerItem(seller1, cat, omsch);
        Item item2 = WebLogic.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }
    
    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = WebLogic.registerUser(email3);
        User seller4 = WebLogic.registerUser(email4);
        Category cat = WebLogic.addCategory("cat3");
        Item item1 = WebLogic.offerItem(seller3, cat, omsch);
        Item item2 = WebLogic.offerItem(seller4, cat, omsch);

        // List ipv ArrayList. getResultList returned List.Vector en kan niet gecast worden naar ArrayList.
        List<Item> res = (List<Item>) WebLogic.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = (List<Item>) WebLogic.findItemByDescription(omsch);
        assertEquals(2, res.size());
    }

    @Test
    public void newBid() {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = WebLogic.registerUser(email);
        User buyer = WebLogic.registerUser(emailb);
        User buyer2 = WebLogic.registerUser(emailb2);

        // First Bid
        Category cat = WebLogic.addCategory("cat9");
        Item item1 = WebLogic.offerItem(seller, cat, omsch);
        Bid new1 = WebLogic.newBid(item1, buyer, WebLogic.addMoney(10, "eur"));
        assertEquals(emailb, new1.getBuyer().getEmail());

        // Lower Bid
        Bid new2 = WebLogic.newBid(item1, buyer2, WebLogic.addMoney(9, "eur"));
        assertNotNull(new2);

        // Higher
        Bid new3 = WebLogic.newBid(item1, buyer2, WebLogic.addMoney(11, "eur"));
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
