package auction.service;

import static org.junit.Assert.*;

import nl.fontys.util.Money;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class AuctionMgrTest {

    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;
    private EntityManager em;
    private EntityManagerFactory factory;
    private util.DatabaseCleaner dbCleaner;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("auction");
        em = factory.createEntityManager();
        dbCleaner = new util.DatabaseCleaner(em);
        dbCleaner.clean();

        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();

    }

    @Test
    public void getItem() {
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registrationMgr.registerUser(email);
        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerFurniture(seller1, cat, omsch, "iron");
        Item item2 = auctionMgr.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registrationMgr.registerUser(email3);
        User seller4 = registrationMgr.registerUser(email4);
        Category cat = new Category("cat3");
        Item item1 = sellerMgr.offerFurniture(seller3, cat, omsch, "iron");
        Item item2 = sellerMgr.offerFurniture(seller4, cat, omsch, "iron");

        // List ipv ArrayList. getResultList returned List.Vector en kan niet gecast worden naar ArrayList.
        List<Item> res = (List<Item>) auctionMgr.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = (List<Item>) auctionMgr.findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registrationMgr.registerUser(email);
        User buyer = registrationMgr.registerUser(emailb);
        User buyer2 = registrationMgr.registerUser(emailb2);
        // eerste bod
        Category cat = new Category("cat9");
        Item item1 = sellerMgr.offerPainting(seller, cat, omsch, "Mona Lisa", "Leonardo Da Vinci");
        Bid new1 = auctionMgr.newBid(item1, buyer, new Money(10, "eur"));
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Bid new2 = auctionMgr.newBid(item1, buyer2, new Money(9, "eur"));
        assertNull(new2);

        // hoger bod
        Bid new3 = auctionMgr.newBid(item1, buyer2, new Money(11, "eur"));
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }

    @Test
    public void newBidTest() {
        Category cat = new Category("cat1");
        User user = registrationMgr.registerUser("test@test.io");
        Item item = sellerMgr.offerPainting(user, cat, "Awesome PAinting", "Mona Lisa", "Leonardo Da Vinci");

        assertEquals(1, user.numerOfOfferedItems());
        assertNull(item.getHighestBid());

        Money money = new Money(20, "Euro");

        Bid bid = auctionMgr.newBid(item, user, money);

        assertNotNull(item.getHighestBid());
        assertEquals(item, bid.getItem());
        assertEquals(user, bid.getBuyer());
        assertEquals(money, bid.getAmount());

        Money money1 = new Money(50, "Euro");

        Bid bid1 = auctionMgr.newBid(item, user, money1);
        assertEquals(money1, bid1.getAmount());
        assertEquals(user, bid1.getBuyer());
        assertEquals(item, bid1.getItem());

        Money money2 = new Money(35, "Euro");

        Bid bid2 = auctionMgr.newBid(item, user, money2);
        assertNull(bid2);
        assertEquals(money1, item.getHighestBid().getAmount());

    }
}
