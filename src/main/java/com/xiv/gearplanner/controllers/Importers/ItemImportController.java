package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.Item;
import com.xiv.gearplanner.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemImportController {
    JsonNode itemsNode;
    ObjectMapper objectMapper;
    ItemService itemDao;

    @Autowired
    public ItemImportController(ItemService itemDao) {
        this.itemDao = itemDao;
    }
    @GetMapping("/import/items")
    public String ReadJson(Model model) throws IOException {
        objectMapper = new ObjectMapper();
        System.out.println("=====OPENING FILE=====");
        itemsNode = objectMapper.readTree(new File("src/main/resources/static/data/item.json"));

        List<Item> items = new ArrayList<>();

        System.out.println("=====LOOPING=====");
        for (JsonNode item : itemsNode) {

            System.out.println("==ADDING ITEM===");

           if(item != null) {
               String name = item.get("name").asText();
                System.out.print(name);
                Integer iLvl = Integer.parseInt(item.get("level_item").asText());
                System.out.print(iLvl);
                Integer equipLevel = Integer.parseInt(item.get("level_equip").asText());
                System.out.print(equipLevel);
                Long xivdbID = item.get("id").asLong();
                String icon = item.get("icon").asText();
                String lodestoneId = (item.get("lodestone_id").asText() != null) ? item.get("lodestone_id").asText() : "" ;
                items.add(new Item(name,  icon,  iLvl,  equipLevel,  xivdbID,  lodestoneId));
           }
        }

        System.out.println("=====SAVING=====ITEMS====" + items.size() + "TOTAL ====");
        itemDao.getItems().saveAll(items);


        System.out.println("===RENDER VIEW===");
        model.addAttribute("itemCount", items.size());

        return "import/items";

    }
}
