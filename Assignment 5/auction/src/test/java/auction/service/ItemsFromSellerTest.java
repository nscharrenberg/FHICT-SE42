package auction.service;

import org.junit.Ignore;
import javax.persistence.*;
import util.DatabaseCleaner;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemsFromSellerTest {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auction");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;
    private util.DatabaseCleaner dbCleaner;

    public ItemsFromSellerTest() {
    }

    @Before
    public void setUp() throws Exception {
        dbCleaner = new util.DatabaseCleaner(em);
        dbCleaner.clean();
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();

    }

    @Test
 //   @Ignore
    public void numberOfOfferdItems() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        User user1 = registrationMgr.registerUser(email);
        assertEquals(0, user1.numerOfOfferedItems());

        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerFurniture(user1, cat, omsch1, "wood");

        assertNotEquals(0, user1.numerOfOfferedItems());
        assertEquals(1, user1.numerOfOfferedItems());
         
        assertEquals(1, item1.getSeller().numerOfOfferedItems());

        User user2 = registrationMgr.getUser(email);
        assertEquals(1, user2.numerOfOfferedItems());
        Item item2 = sellerMgr.offerFurniture(user2, cat, omsch2, "wood");
        assertEquals(2, user2.numerOfOfferedItems());

        User user3 = registrationMgr.getUser(email);
        assertEquals(2, user3.numerOfOfferedItems());

        User userWithItem = item2.getSeller();
        assertEquals(2, userWithItem.numerOfOfferedItems());
        assertNotEquals(3, userWithItem.numerOfOfferedItems());

        assertSame(user3, userWithItem);
        assertEquals(user3, userWithItem);
    }

    @Test
//    @Ignore
    public void getItemsFromSeller() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        Category cat = new Category("cat2");

        User user10 = registrationMgr.registerUser(email);
        Item item10 = sellerMgr.offerPainting(user10, cat, omsch1, "This Painting", "Someone");
        Iterator<Item> it = user10.getOfferedItems();
        // testing number of items of java object
        assertTrue(it.hasNext());
        
        // now testing number of items for same user fetched from db.
        User user11 = registrationMgr.getUser(email);
        Iterator<Item> it11 = user11.getOfferedItems();
        assertTrue(it11.hasNext());
        it11.next();
        assertFalse(it11.hasNext());
        
        
        User user20 = registrationMgr.getUser(email);
        Item item20 = sellerMgr.offerPainting(user20, cat, omsch2, "This Painting", "Someone");
        Iterator<Item> it20 = user20.getOfferedItems();
        assertTrue(it20.hasNext());
        it20.next();
        assertTrue(it20.hasNext());


        User user30 = item20.getSeller();
        Iterator<Item> it30 = user30.getOfferedItems();
        assertTrue(it30.hasNext());
        it30.next();
        assertTrue(it30.hasNext());
    }
}
