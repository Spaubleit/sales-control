package usr.krina.salescontrol.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Product implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double wholesalePrice;

    @Column(nullable = false)
    private double retailPrice;

    @OneToMany(mappedBy = "product")
    private List<Wholesale> wholesales;

    public Product() {
    }

    public Product(String name, double wholesalePrice, double retailPrice) {
        this.name = name;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public List<Wholesale> getWholesales() {
        return wholesales;
    }

    public void setWholesales(List<Wholesale> wholesales) {
        this.wholesales = wholesales;
    }
}
