package auction.service;

import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.User;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgrTest {

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = WebLogic.registerUser("xx@nl");
        Category cat = WebLogic.addCategory("cat1");
        Item item1 = WebLogic.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";
        
    
        User seller = WebLogic.registerUser("sel@nl");
        User buyer = WebLogic.registerUser("buy@nl");
        Category cat = WebLogic.addCategory("cat1");
        
            // revoke before bidding
        Item item1 = WebLogic.offerItem(seller, cat, omsch);
        boolean res = WebLogic.revokeItem(item1);
        assertTrue(res);
        int count = WebLogic.findItemByDescription(omsch).size();
        assertEquals(0, count);
        
            // revoke after bid has been made
        Item item2 = WebLogic.offerItem(seller, cat, omsch2);
        WebLogic.newBid(item2, buyer, WebLogic.addMoney(100, "Euro"));
        boolean res2 = WebLogic.revokeItem(item2);
        assertFalse(res2);
        int count2 = WebLogic.findItemByDescription(omsch2).size();
        assertEquals(1, count2);
    }

}
