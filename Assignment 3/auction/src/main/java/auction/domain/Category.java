package auction.domain;

import javax.persistence.*;

@Entity(name = "categories")
@NamedQueries({
        @NamedQuery(name = "Category.getAll", query = "SELECT c FROM categories as c"),
        @NamedQuery(name = "Category.count", query = "SELECT count(c) FROM categories as c"),
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public Category() {
        description = "undefined";
    }

    public Category(String description) {
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }
}
