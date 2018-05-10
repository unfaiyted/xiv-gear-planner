package com.xiv.gearplanner.controllers.Importers;

import com.xiv.gearplanner.models.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

public class DataImportController {


    //https://api.xivdb.com/data/patchlist?pretty=1


    //https://api.xivdb.com/item?columns=id,name,icon,level_item,lodestone_id,slot_equip&pretty=1

    @GetMapping("/import/data/items")
    public  String importItems() {

        //Validate starting item import
        return "/import/data/items";
    }

    @PostMapping("/import/data/items")
    public String completeImportItems() {
        //turn to json string then import as
        // object
        return "/import/data/items";
    }


}
