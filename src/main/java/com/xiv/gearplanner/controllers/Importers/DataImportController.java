package com.xiv.gearplanner.controllers.Importers;

import com.xiv.gearplanner.models.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


public class DataImportController {


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
