package com.xiv.gearplanner.controllers.api;

import com.xiv.gearplanner.models.shops.Shop;
import com.xiv.gearplanner.services.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/shop")
public class ApiShopController {
   private ShopService shopDao;


    public ApiShopController(ShopService shopDao) {
        this.shopDao = shopDao;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Shop getShopById(@PathVariable Long id) {
        if (shopDao.getShops().findById(id).isPresent()) {
            return shopDao.getShops().findById(id).get();
        }
        return null;
    }

    @RequestMapping(value = "/byItem/{itemId}", method = RequestMethod.GET, produces = "application/json")
    public List<Shop> getShopItemByItemId(@PathVariable Long itemId) {

           List<Shop> shops = shopDao.getShops().findGilShopWithItemId(itemId);
            shops.addAll(shopDao.getShops().findSpecialShopsWithItemId(itemId));

            return shops;

    }

}
