package com.xiv.gearplanner.services;


import com.xiv.gearplanner.repositories.Shops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private Shops shops;

    @Autowired
    public ShopService(Shops shops) {
        this.shops = shops;
    }

    public Shops getShops() {
        return shops;
    }
}
