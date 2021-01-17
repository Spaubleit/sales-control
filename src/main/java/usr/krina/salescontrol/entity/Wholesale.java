package usr.krina.salescontrol.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Wholesale {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    public Wholesale() {
    }

    public Wholesale(int count, Date date, Product product, Contractor contractor) {
        this.count = count;
        this.date = date;
        this.product = product;
        this.contractor = contractor;
    }

    public Wholesale update(Wholesale wholesale) {
        this.count = wholesale.count;
        this.date = wholesale.date;
        this.product = wholesale.product;
        this.contractor = wholesale.contractor;
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

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }
}
