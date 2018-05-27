package com.xiv.gearplanner.controllers.api;


import com.xiv.gearplanner.models.inventory.Item;
import com.xiv.gearplanner.services.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/item")
public class ApiItemController {
    private ItemService itemDao;


    public ApiItemController(ItemService itemDao) {
         this.itemDao = itemDao;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Item getItemInJSON(@PathVariable Long id) {
         if (itemDao.getItems().findById(id).isPresent()) {
             return itemDao.getItems().findById(id).get();
         }
         return null;
    }






}
