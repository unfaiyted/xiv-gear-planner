package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.ImporterResult;
import com.xiv.gearplanner.models.inventory.Item;
import com.xiv.gearplanner.models.shops.GilShop;
import com.xiv.gearplanner.models.shops.Shop;
import com.xiv.gearplanner.services.ItemService;
import com.xiv.gearplanner.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopImportController {
    private JsonNode data;
    private ObjectMapper objectMapper;

    // Store results from imports
    List<ImporterResult> results = new ArrayList<>();

    // Object that has all items stored in it
    private ItemService itemDao;
    private ShopService shopDao;

    // Location of Json files to be imported
    private String directory = "src/main/resources/static/data/additional/";

    @Autowired
    public ShopImportController(ItemService itemDao, ShopService shopDao) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
    }

    @GetMapping("/import/data/shops")
    public String getShops(Model model) {

        // Files to be imported, results returned.
        results.add(importGilShopNames(getFileAsJson(directory +"GilShop.csv.json")));
        results.add(importGilShopItems(getFileAsJson(directory +"GilShopItem.csv.json")));

        //GCShop.csv.json
        //FccShop.csv.json
        //DisposalShop.csv.json
        // SpecialShop.csv.json
        // GilShop.csv.json


        model.addAttribute("results", results);
        return "/import/results";

    }

    // Imports shop names
    private ImporterResult importGilShopNames(JsonNode file) {
        List<GilShop> shops = new ArrayList<>();
        List<Integer> originalIds = shopDao.getShops().getAllOriginalIds();

        // Job Importing
        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {
                Integer originalId = record.get("#").asInt();
                String name = record.get("Name").asText();

                if(!originalIds.contains(originalId)) {
                    shops.add(new GilShop(originalId, name));
                }
            }
            i++;
        }

        shopDao.getShops().saveAll(shops);

        return new ImporterResult("GilShopNames", (long) shops.size());
    }


    // Names associated with Gil Shops
    private ImporterResult importGilShopItems(JsonNode file) {

        List<Integer> originalIds = shopDao.getShops().getAllOriginalIds();

        // Job Importing
        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {

                Integer originalShopId = record.get("#").asInt();
                Integer originalItemId = record.get("Item").asInt();


                // Item associated
              Item item = itemDao.getItems().findByOriginalId(originalItemId);
              Shop shop = shopDao.getShops().findByOriginalId(originalShopId);

              if(item != null && shop != null) {

                  if(!shopDao.getShops().existsInShop(shop.getId(), item.getId())) {
                      shopDao.getShops().addGilShopItem(shop.getId(), item.getId());
                  }
              }
            }
            i++;
        }

        return new ImporterResult("GilShopItems", (long) 1);
    }



    private ImporterResult importSpecialShop(JsonNode file) {

        // Job Importing
        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {
                Integer originalId = record.get("#").asInt();
                String name = record.get("Name").asText();


                for (int j = 0; j < 60; j++) {
                        for(int x = 0; x < 3; x++) {

                        Long itemReceiveId = record.get("Item{Receive}["+ j +"]["+ x +"]").asLong();
                        Long itemReceiveCount = record.get("Count{Receive}["+ j +"]["+ x +"]").asLong();
                        Long itemReceiveHQ = record.get("HQ{Receive}["+ j +"]["+ x +"]").asLong();



                    }
                }

//"Item{Cost}[0][0]"  "Count{Cost}[0][0]" "HQ{Cost}[0][0]" "CollectabilityRating{Cost}[0][0]"
//                                             "Item{Receive}[0][1]" = "Item{Receive}[59][1]"
//
//                "Item{Cost}[0][1]" "Count{Cost}[0][1]" "HQ{Cost}[0][1]"  "CollectabilityRating{Cost}[0][1]"
//
//                "Item{Cost}[0][2]"  "Quest{Item}[0]" "Quest{Shop}"


            }
            i++;
        }

        return new ImporterResult("SpecialShops", (long) 2);
    }






    // Get File JSON and convert to object.
    private JsonNode getFileAsJson(String pathname)   {
        try {
            objectMapper = new ObjectMapper();
            System.out.println("=====OPENING FILE: " + pathname);
            data = objectMapper.readTree(new File(pathname));
            System.out.println("=====DONE=====");
            return data;
        } catch(IOException err) {
            System.out.println("Error reading JSON FILE");
            return data;
        }

    }

}
