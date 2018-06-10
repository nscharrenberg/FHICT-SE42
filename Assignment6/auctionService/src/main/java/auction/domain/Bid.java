package auction.domain;

import java.io.Serializable;
import static java.lang.System.console;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "bids")
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "SELECT u FROM bids as u"),
        @NamedQuery(name = "Bid.count", query = "SELECT count(u) FROM bids as u"),
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @XmlElement
    @Column (name = "time")
    private FontysTime time;

    @ManyToOne
    @XmlElement(required = true)
    private User buyer;

    @Embedded
    @XmlElement(required = true)
    private Money amount;

    public Bid() {

    }

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public FontysTime getTime() {
        return time;
    }
    
    public void setTime(FontysTime time) {
        this.time = time;
    }

    public User getBuyer() {
        return buyer;
    }
    
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Money getAmount() {
        return amount;
    }
    
    public void setAmount(Money amount) {
        this.amount = amount;
    }
    
    
    
    
}
