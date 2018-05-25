package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class SpecialShopPurchasable  {

    @ManyToMany
    private List<Purchasable> receivables;
    @ManyToMany
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


}
