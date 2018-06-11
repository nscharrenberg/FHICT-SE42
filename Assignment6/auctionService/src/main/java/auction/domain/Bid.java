package auction.domain;

import java.io.Serializable;
import static java.lang.System.console;
import java.util.Objects;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "SELECT u FROM Bid as u"),
        @NamedQuery(name = "Bid.count", query = "SELECT count(u) FROM Bid as u"),
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bid other = (Bid) obj;
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.buyer, other.buyer)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return true;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.time);
        hash = 79 * hash + Objects.hashCode(this.buyer);
        hash = 79 * hash + Objects.hashCode(this.amount);
        return hash;
    }
}
