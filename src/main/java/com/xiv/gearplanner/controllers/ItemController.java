package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
    private ItemService itemDao;

    @Autowired
    public ItemController(ItemService itemDao) {
        this.itemDao = itemDao;
    }


    /*
    * TODO: Make searchable, pageable, and auto show tooltip
    * */
    @GetMapping("/item/view")
    public String viewItems(Model model,  @PageableDefault(value=50) Pageable pageable ) {
        model.addAttribute("item",itemDao.getItems().findAll(pageable));
        return "item/view";
    }

}
