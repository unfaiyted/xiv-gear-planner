package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class SpecialShopPurchasable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Purchasable> receivables;
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Purchasable> costs;

    public SpecialShopPurchasable() {}

    public SpecialShopPurchasable(List<Purchasable> receivables, List<Purchasable> costs) {
        this.receivables = receivables;
        this.costs = costs;
    }

    public List<Purchasable> getReceivables() {
        return receivables;
    }

    public void setReceivables(List<Purchasable> receivables) {
        this.receivables = receivables;
    }

    public List<Purchasable> getCosts() {
        return costs;
    }

    public void setCosts(List<Purchasable> costs) {
        this.costs = costs;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
