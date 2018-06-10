package auction.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
public class Painting extends Item implements Serializable {
    private String title;
    private String painter;

    public Painting(User seller, Category category, String description, String title, String painter) {
        super(seller, category, description);
        this.title = title;
        this.painter = painter;
    }

    public Painting() {

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }
}
