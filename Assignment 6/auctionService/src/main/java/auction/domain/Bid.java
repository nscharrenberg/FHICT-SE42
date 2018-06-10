package auction.domain;

import java.io.Serializable;
import static java.lang.System.console;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity(name = "bids")
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "SELECT u FROM bids as u"),
        @NamedQuery(name = "Bid.count", query = "SELECT count(u) FROM bids as u"),
})
public class Bid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private FontysTime time;

    @ManyToOne
    private User buyer;

    @Embedded
    private Money amount;
    
    @OneToOne(mappedBy = "highest")
    @JoinColumn(nullable = false)
    private Item item;

    public Bid() {

    }

    public Bid(User buyer, Money amount, Item item) {
        this.buyer = buyer;
        this.amount = amount;
        this.item = item;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
    
    public long getId() {
        return id;
    }
    
    public Item getItem() {
        return item;
    }
}
