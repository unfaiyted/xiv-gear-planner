package com.xiv.gearplanner.services;

import com.xiv.gearplanner.repositories.Shops;
import com.xiv.gearplanner.repositories.SpecialShops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialShopsService {
    private SpecialShops shops;

    @Autowired
    public SpecialShopsService(SpecialShops shops) {
        this.shops = shops;
    }

    public SpecialShops getShops() {
        return shops;
    }


}
