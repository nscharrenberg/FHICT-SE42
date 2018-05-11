package bank.domain;

import bank.dao.AccountDAOJPAImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {

    private EntityManager em;
    private EntityManagerFactory factory;
    private DatabaseCleaner dbCleaner;
    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("bankPU");
        em = factory.createEntityManager();
        dbCleaner = new DatabaseCleaner(em);


    }

    @After
    public void tearDown() throws Exception {
        dbCleaner.clean();
    }

    @Test
    public void AssignmentOne() {
        /*
        * Hoe werken persist en commit in samenhang met de database.
        * 1. AccountId: 1
        * 2.    - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
        *       - SELECT LAST_INSERT_ID()
        * 3. Account:
        *       - ID:           1
        *       - ACCOUNTNR:    111
        *       - BALANCE:      0
        *       - THRESHOLD:    0
         */
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        // TODO: verklaar en pas eventueel aan
        /*
         * Er is nog geen commit geweest. De database heeft dus nog geen ID assigned dmv Auto Increment.
         * Hierdoor is het ID dus nog null.
         */

        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        /*
         * Doordat er Autoincrement is, zorgt de database ervoor dat er een Id wordt assigned.
         * Zodra iemand een nieuwe table toevoegt dan wordt het id + 1.
         * De eerste resultaat zal dan Id 1 hebben, het 2e zal id 2 hebben etc...
         */

        assertTrue(account.getId() > 0L);
    }

    @Test
    public void AssignmentTwo() {
        /*
         * Rollback
         * 1. accountsList Size: 0
         * 2.   - SELECT COUNT(ID) FROM ACCOUNT
         * 3. Leeg, er zijn geen gebruikers.
         * 4. Er zijn geen accounts in de database. De select query die de hoeveelheid resultaten opgehaald,
         *      is dan ook 0.
         */
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        int count = new AccountDAOJPAImpl(em).count();
        System.out.println("AccountList Size: " + count);
        assertEquals(0, count);
    }

    @Test
    public void AssignmentThree() {
        /*
         * Flushen maar
         * 1.   Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         * 3.   Er is een account in de database.
         * 4. Het account wordt persist voor de EntityManager, daarna gecontroleerd dmv flush, en daarna pas gecommit naar de database.
         */
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        /*
         * Er is nog geen commit geweest. De database heeft dus nog geen ID assigned dmv Auto Increment.
         * Hierdoor is het ID dus nog null.
         */
        assertEquals(expected, account.getId());
        em.flush();
        /*
         * De data wordt gecontrolleerd maar nog niet gecommit naar de database.
         */
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
        /*
         * De data staat nu in de database, aangezien er nu gecommit is.
         * Het account zal nu een Id hebben, en gevonden kunnen worden in de database.
         */
    }

    @Test
    public void AssignmentFour() {
        /*
         * Veranderingen na de persist
         * 1.   Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         * 3. Er is een account met accountnr 114 en een balance van 400.
         */
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        /*
         * De balance wordt geSet binnen een transactie en de transactie is daarna gecommit.
         * De juiste waarde (expectedBalance) is opgeslagen en opgehaald.
         */
        Long  cid = account.getId();
        /*
         * account = null is overbodig aangezien er niets mee wordt gedaan, totdat er een nieuwe instantie van wordt aangeroepen.
         * Hierdoor wordt de null instantie nooit gebruikt, en is deze dus overbodig.
         */
//        account = null;
        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        /*
         * Er wordt een 2e EntityManager aangemaakt.
         * Op het punt dat hij zoekt naar het account, vindt hij deze en haalt hij het juiste saldo ervan op.
         */
        assertEquals(expectedBalance, found.getBalance());
    }

    @Test
    public void assignmentFive() {
        /*
         * Refresh
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         *      - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)
         *      - UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)
         *      - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)
         * 3. Er is een account met accountnr 114 dat een balance van 650 heeft. (waarde van newExpected)
         */
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        /*
         * De balance wordt geSet binnen een transactie en de transactie is daarna gecommit.
         * De juiste waarde (expectedBalance) is opgeslagen en opgehaald.
         */
        Long  cid = account.getId();
        /*
         * account = null is overbodig aangezien er niets mee wordt gedaan, totdat er een nieuwe instantie van wordt aangeroepen.
         * Hierdoor wordt de null instantie nooit gebruikt, en is deze dus overbodig.
         */
//        account = null;
        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        /*
         * Er wordt een 2e EntityManager aangemaakt.
         * Daarna wordt er een nieuw Account geinstantieerd, deze instantie is de eerder aangemaakte "account".
         * Er wordt dus een Account gezocht met het id van het "account" Object, en het gevonden Account wordt geinstantieerd in het "found" Object.
         */
        assertEquals(expectedBalance, found.getBalance());
        Long newExpected = 650L;
        found.setBalance(newExpected);
        em2.getTransaction().commit();
        /*
         * De balance wordt in het 2e account geSet binnen een transactie en de transactie is daarna gecommit.
         * De juiste waarde (newExpected) is opgeslagen en opgehaald bij het 2e account.
         */
        em.refresh(account);
        assertEquals(newExpected, account.getBalance());
        /*
         * Het nieuwe bedrag komt overeen.
         */
    }

    @Test
    public void assignmentSixS1() {
        Account acc = new Account(1L);

        /*
         * Scenario 1
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         * 3. Er is een account met een balance van 100.
         * 4. Er wordt een account aangemaakt met een balance van 100.
         *      Vervolgens wordt het accountNr opgehaald uit de database en gecontrolleerd, of de database waardes overeenkomen met de waardes in de database.
         */
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();

        assertEquals(balance1, acc.getBalance());

        Account found = new AccountDAOJPAImpl(em).findByAccountNr(acc.getAccountNr());

        assertEquals(balance1, found.getBalance());
    }

    @Test
    public void assignmentSixS2() {
        /*
         * Scenario 2
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         *      - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ACCOUNTNR = ?)
         * 3. acc9 is opgeslagen in de database met een balans van 422 (waarde van balance2b)
         */
        Account acc;
        Account acc9;

        // scenario 2
        Long balance2a = 211L;
        Long balance2b = balance2a+balance2a;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2b);
        em.getTransaction().commit();

        assertEquals(balance2a, acc.getBalance());
        assertEquals(balance2b, acc9.getBalance());

        Account found = new AccountDAOJPAImpl(em).findByAccountNr(acc9.getAccountNr());

        assertEquals(balance2b, found.getBalance());
    }

    @Test
    public void assignmentSixS3() {
        /*
         * Scenario 3
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         *      - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ACCOUNTNR = ?)
         * 3. Er wordt een account aangemaakt met een balance van 322 (waarde van balance3b).
         */
        Account acc;
        Account acc2;

        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);

        /*
         * Verklaring:
         * acc is detached, en dus niet meer 'verbonden' met het opgeslagen Account object, dit komt doordat acc2 met deze entiteit gemerged is.
         */
        assertFalse(em.contains(acc));

        /*
         * Verklaring:
         * acc2 is gemerged met de opgeslagen entiteit, daarom komt acc2 wel voor in de database.
         */
        assertTrue(em.contains(acc2));

        /*
         * Verklaring:
         * acc is niet langer verbonden met de entiteit van de database, acc2 is wel verbonden met de entiteit van de database.
         * Ze zijn dus niet gelijk.
         */
        assertNotEquals(acc,acc2);

        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit() ;

        assertEquals(balance3b, acc2.getBalance());
        assertNotEquals(balance3c, acc2.getBalance());

        Account found = new AccountDAOJPAImpl(em).findByAccountNr(acc2.getAccountNr());

        assertEquals(balance3b, found.getBalance());
        assertNotEquals(balance3c, found.getBalance());
    }

    @Test
    public void assignmentSixS4() {
        /*
         * Scenario 4
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         *      - UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)
         * 3. Er wordt een account aangemaakt met een balance van 650.
         */
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        EntityManager em = factory.createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;

        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;

        /*
         * Verklaring:
         * tweedeAccountObject heeft nu dezelfde referentie als account2,als deze dus wordt aangepast dan wordt account2 ook aangepast.
         */
        assertEquals((Long)650L,account2.getBalance()) ;

        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;

        /*
         * Verklaring:
         * account2 heeft hetzelfde id als account, bij het mergen overschrijft account2 dus de data in de database met de info uit account2 over account heen.
         */
        assertSame(account,account2) ;

        /*
         * Verklaring:
         * EntityManager houd account2 bij vanwege de gemaakte merge.
         */
        assertTrue(em.contains(account2)) ;

        /*
         * tweedeAccountObject is nooit als persistancy toegevoegd.
         */
        assertFalse(em.contains(tweedeAccountObject)) ;

        tweedeAccountObject.setBalance(850l) ;

        /*
         * Verklaring:
         * De waarde is de database overschreven en accout is persist, waardoor deze een nieuwe waarde heeft.
         */
        assertEquals((Long)650L,account.getBalance()) ;

        /*
         * Verklaring:
         * account2 is niet meer aangepast aangezien het referentie object veranderd is.
         */
        assertEquals((Long)650L,account2.getBalance()) ;
        em.getTransaction().commit() ;
        em.close();
    }

    @Test
    public void assignmentSeven() {
        /*
         * Find en Clear
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         *      - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)
         * 3. Er wordt een account aangemaakt.
         */
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        /*
         * Scenario 1
         * find(<Class>, Primary key) vindt het object in de database van de opgegeven tabel met de primary key (het Id in dit geval)
         */
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        /*
         * Scenario 2
         * clear() laat alle verbindingen met de database vervallen. Hierdoor zijn de teruggekregen objecten niet hetzelfde als dat ze voorheen waren.
         */
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);

    }

    @Test
    public void assignmentEight() {
        /*
         * Remove
         * 1. Goed
         * 2.   - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
         *      - SELECT LAST_INSERT_ID()
         * 3. Het account staat nog in de Database.
         * 4. Het wordt nooit doorgegeven aan de database om het account te verwijderen. (er wordt geen commit gemaakt)
         */
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
    }

    @Test
    public void assignmentNine() {
        /*
         * Sequence:
         * Geen verschil in de resultaten en ook geen verschil in de database structuur.
         *
         * Table:
         * De meeste krijgen een AssertionError met failNotNull, expected null, but was:
         * Vanaf Assignment 5 krijg je Wait Time Exceeded, en zeer lang wachten van de Tests.
         * De database heeft een nieuwe table erbij genaamd Sequence, met een SEQ_NAME en SEQ_COUNT
         */
        assertTrue(true);
    }
}