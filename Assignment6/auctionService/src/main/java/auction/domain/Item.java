package auction.domain;

import java.io.Serializable;
import nl.fontys.util.Money;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.getAll", query = "SELECT i FROM Item as i"),
        @NamedQuery(name = "Item.count", query = "SELECT count(i) FROM Item as i"),
        @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :id"),
        @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description"),
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Comparable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @XmlElement(required = true)
    private User seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description",
                    column = @Column(name = "cat_description"))
    })
    @XmlElement(required = true)
    private Category category;

    private String description;

    @OneToOne
    @XmlElement(name = "highest")
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
    
    public void setId(Long id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }
    public void setSeller(User user) { this.seller = user; }

    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        
        highest = new Bid(buyer, amount);
        return highest;
    }

    @Override
    public int compareTo(Object arg0) {
        if(arg0 instanceof Item) {
            Item oItem = (Item)arg0;
            return oItem.getHighestBid().getAmount().compareTo(this.getHighestBid().getAmount());
        }

        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Item)) {
            return false;
        }
        
        Item other = (Item) o;
        return id == null ? other.id == null : id.equals(other.id);
}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }
   }
