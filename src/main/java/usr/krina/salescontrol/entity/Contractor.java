package usr.krina.salescontrol.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Contractor implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "contractor")
    private List<Wholesale> wholesales;

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

    public List<Wholesale> getWholesales() {
        return wholesales;
    }

    public void setWholesales(List<Wholesale> wholesales) {
        this.wholesales = wholesales;
    }
}
