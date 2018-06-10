package auction.domain;

import java.io.Serializable;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT u FROM users as u"),
        @NamedQuery(name = "User.count", query = "SELECT count(u) FROM users as u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM users as u WHERE u.email = :email"),
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "seller")
    private Set<Item> offeredItems;

    public User() {
        this.offeredItems = new HashSet<>();

    }

    public User(String email) {
        this.offeredItems = new HashSet<>();
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Iterator<Item> getOfferedItems() {
        return Collections.unmodifiableSet(this.offeredItems).iterator();
    }

    public int numerOfOfferedItems() {
        return Collections.unmodifiableSet(this.offeredItems).size();
    }

    void addItem(Item item) {
        this.offeredItems.add(item);
    }
}
