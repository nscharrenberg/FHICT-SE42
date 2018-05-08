package bank.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;

import static org.junit.Assert.*;

public class AccountTest {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU");
    private EntityManager em;
    @Before
    public void setUp() throws Exception {
         em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void persistAccount() {

        Account account = new Account(111L);
        em.getTransaction().begin();
    }
}