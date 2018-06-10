package auction.service;

import auction.webservice.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class JPARegistrationMgrTest {
    // TODO: Fix hashcode
    
    @Test
    public void registerUser() {
        User user1 = WebLogic.registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = WebLogic.registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = WebLogic.registerUser("xxx2@yyy2");
//        assertSame(user2bis, user2);
        //geen @ in het adres
        assertNull(WebLogic.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = WebLogic.registerUser("xxx5@yyy5");
        User userGet = WebLogic.getUser("xxx5@yyy5");
//        assertSame(userGet, user1);
        assertNull(WebLogic.getUser("aaa4@bb5"));
        WebLogic.registerUser("abc");
        assertNull(WebLogic.getUser("abc"));
    }

    @Test
    public void getUsers() {
        List<User> users = WebLogic.getUsers();
        assertEquals(3, users.size());

        User user1 = WebLogic.registerUser("xxx8@yyy");
        users = WebLogic.getUsers();
        assertEquals(4, users.size());
//        assertSame(users.get(0), user1);


        User user2 = WebLogic.registerUser("xxx9@yyy");
        users = WebLogic.getUsers();
        assertEquals(5, users.size());

        WebLogic.registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = WebLogic.getUsers();
        assertEquals(5, users.size());
    }
}
