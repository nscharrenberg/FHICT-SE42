package auction.domain;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT u FROM users as u"),
        @NamedQuery(name = "User.count", query = "SELECT count(u) FROM users as u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM users as u WHERE u.email = :email"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "seller")
    private Set<Item> offeredItems;

    public User() {
    }

    public User(String email) {
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

    private void addItem(Item item) {
        this.offeredItems.add(item);
        item.setSeller(this);
    }

    public void addItemToSeller(Item item) {
        if (item != null && !item.getDescription().isEmpty()){
            addItem(item);
        }
    }
}
