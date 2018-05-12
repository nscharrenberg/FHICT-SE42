package auction.domain;

import javax.persistence.*;

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
}
