package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity(name = "bids")
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "SELECT u FROM bids as u"),
        @NamedQuery(name = "Bid.count", query = "SELECT count(u) FROM bids as u"),
})
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private FontysTime time;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User buyer;

    private Money amount;

    @OneToOne(orphanRemoval = true)
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

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
