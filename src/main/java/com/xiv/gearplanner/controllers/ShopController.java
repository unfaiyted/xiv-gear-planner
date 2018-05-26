package com.xiv.gearplanner.controllers;


import com.xiv.gearplanner.models.shops.GilShop;
import com.xiv.gearplanner.models.shops.Shop;
import com.xiv.gearplanner.models.shops.SpecialShop;
import com.xiv.gearplanner.services.ShopService;
import com.xiv.gearplanner.services.SpecialShopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ShopController {
    private ShopService shopDao;
    private SpecialShopsService specialShopsDao;

    @Autowired
    public ShopController(ShopService shopDao, SpecialShopsService specialShopDao) {
        this.shopDao = shopDao;
        this.specialShopsDao = specialShopDao;

    }


    @GetMapping("/shops/view")
    public String viewShops(Model model, @PageableDefault(value = 2) Pageable pageable) {
        model.addAttribute("shops", specialShopsDao.getShops().findAll(pageable));
        return "shops/view2";
    }


    @GetMapping("/shops/view/{id}")
    public String viewShop(Model model, @PathVariable Long id) {

        Shop shop = shopDao.getShops().findByIdWithPurchasable(id);


        String shopType = "default";



        if(shop instanceof SpecialShop) {
             shopType = "Special";
        }
        if(shop instanceof GilShop) {
            shopType = "Gil";
            System.out.println(shop.toString());
        }

            model.addAttribute("shopType",shopType);
            model.addAttribute("shops",shop);
        return "shops/view";

    }

}
