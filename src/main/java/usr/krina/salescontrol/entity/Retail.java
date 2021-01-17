package usr.krina.salescontrol.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Retail {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private int count;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Retail() {
    }

    public Retail(int count, Date date, Product product) {
        this.count = count;
        this.date = date;
        this.product = product;
    }

    public Retail update(Retail retail) {
        this.count = retail.count;
        this.date = retail.date;
        this.product = retail.product;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
