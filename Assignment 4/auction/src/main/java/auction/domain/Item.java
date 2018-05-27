package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "items")
@NamedQueries({
        @NamedQuery(name = "Item.getAll", query = "SELECT i FROM items as i"),
        @NamedQuery(name = "Item.count", query = "SELECT count(i) FROM items as i"),
        @NamedQuery(name = "Item.findById", query = "select i from items as i where i.id = :id"),
        @NamedQuery(name = "Item.findByDescription", query = "select i from items as i where i.description = :description"),
})
public class Item implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description",
                    column = @Column(name = "cat_description"))
    })
    private Category category;

    private String description;

    @OneToOne(mappedBy = "item")
    private Bid highest;

    public Item() {

    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }
    public void setSeller(User user) { this.seller = user; }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount, this);
        return highest;
    }

    public int compareTo(Object arg0) {
        if(arg0 instanceof Item) {
            Item oItem = (Item)arg0;
            return oItem.getHighestBid().getAmount().compareTo(this.getHighestBid().getAmount());
        }

        return -1;
    }

    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(!(o instanceof Item)) {
            return false;
        }

        Item other = (Item)o;

        if(this.getSeller() != other.getSeller()) {
            return false;
        }

        if(this.getCategory() != other.getCategory()) {
            return false;
        }

        if(!this.getDescription().equals(other.getDescription())) {
            return false;
        }

        if(this.getHighestBid() != other.getHighestBid()) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return Objects.hash(seller, category, description, highest);
    }
}
