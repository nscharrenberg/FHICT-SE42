package auction.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
public class Furniture extends Item implements Serializable {
    private String material;

    public Furniture(User seller, Category category, String description, String material) {
        super(seller, category, description);
        this.material = material;
    }

    public Furniture() {
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
