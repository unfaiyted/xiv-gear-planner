package com.xiv.gearplanner.models.shops;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SpecialShop extends Shop {

    @ManyToMany(cascade = CascadeType.ALL)
    private List<SpecialShopPurchasable> purchasables = new ArrayList<>();

    public SpecialShop(){}

    public SpecialShop(Integer originalId, String name, List<SpecialShopPurchasable> purchasables) {
        super(originalId, name);
        this.purchasables = purchasables;
    }

    public List<SpecialShopPurchasable> getPurchasables() {
        return purchasables;
    }

    public void setPurchasables(List<SpecialShopPurchasable> purchasables) {
        this.purchasables = purchasables;
    }


    @Override
    public String toString() {
        return "SpecialShop{" +
                "purchasables=" + purchasables +
                "} " + super.toString();
    }
}
