package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.ImporterResult;
import com.xiv.gearplanner.models.inventory.Item;
import com.xiv.gearplanner.models.shops.*;
import com.xiv.gearplanner.repositories.SpecialShops;
import com.xiv.gearplanner.services.ItemService;
import com.xiv.gearplanner.services.ShopService;
import com.xiv.gearplanner.services.SpecialShopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    private SpecialShopsService specialShopDao;

    // Location of Json files to be imported
    private String directory = "src/main/resources/static/data/additional/";

    @Autowired
    public ShopImportController(ItemService itemDao, ShopService shopDao, SpecialShopsService specialShopDao) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
        this.specialShopDao = specialShopDao;
    }

    @GetMapping("/import/data/shops")
    public String getShops(Model model) {

        // Files to be imported, results returned.
       // results.add(importGilShopNames(getFileAsJson(directory +"GilShop.csv.json")));
       results.add(
               importGilShopItemsWithShops(
                       getFileAsJson(directory +"GilShopItem.csv.json"),
               populateGilShopNames(
                       getFileAsJson(directory +"GilShop.csv.json"))
               ));
        // results.add(importSpecialShop(getFileAsJson(directory + "SpecialShop.csv.json")));

        //GCShop.csv.json
        //FccShop.csv.json
        //DisposalShop.csv.json
        // SpecialShop.csv.json
        // GilShop.csv.json


        model.addAttribute("results", results);
        return "/import/results";

    }

    // Imports shop names
    private HashMap<Integer, GilShop> populateGilShopNames(JsonNode file) {
        HashMap<Integer, GilShop> shops = new HashMap<>();
        List<Integer> originalIds = shopDao.getShops().getAllOriginalIds();

        // Job Importing
        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {
                Integer originalId = record.get("#").asInt();
                String name = record.get("Name").asText();

                if(!originalIds.contains(originalId)) {
                    shops.put(originalId, new GilShop(originalId, name));
                }
            }
            i++;
        }

        return shops;

    }


    // Items associated with Gil Shops
    private ImporterResult importGilShopItemsWithShops(JsonNode file, HashMap<Integer, GilShop> shops) {
        // Job Importing
        HashMap<Integer, Item> items = new HashMap<>();

        List<Item> itemList = itemDao.getItems().findAll();

        for(Item item : itemList) {
            items.put(item.getOriginalId(), item);
        }

        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {

                Integer originalShopId = record.get("#").asInt();
                Integer originalItemId = record.get("Item").asInt();

                // Item associated
                Item item = items.get(originalItemId);
                GilShop shop = shops.get(originalShopId);

              if(item != null && shop != null) {
                  shop.addItems(item);

              }
            }
            i++;
        }


        List<GilShop> list = new ArrayList<>(shops.values());

        shopDao.getShops().saveAll(list);

        return new ImporterResult("GilShopItems", (long) 1);
    }


    private ImporterResult importSpecialShop(JsonNode file) {

        List<SpecialShop> shops = new ArrayList<>();

        List<Item> items = itemDao.getItems().findAll();

        // Job Importing
        int i = 0;
        for (JsonNode record : file) {
            //Add to array
            if (i >= 1) {
                List<SpecialShopPurchasable> purchasable = new ArrayList<>();

                Integer originalId = record.get("#").asInt();
                String name = record.get("Name").asText();

                if(specialShopDao.getShops().findAllByOriginalId(originalId) != null) {
                    continue;
                }

                System.out.println("====> SHOP NAME: "+ name);

                for (int j = 0; j < 60; j++) {
                     List<Purchasable> receivable = new ArrayList<>();
                     List<Purchasable> costs = new ArrayList<>();

                    for(int x = 0; x < 2; x++) {
                        // Fill items in list Receipt
                        Integer itemReceiveId = record.get("Item{Receive}["+ j +"]["+ x +"]").asInt();
                        Integer itemReceiveCount = record.get("Count{Receive}["+ j +"]["+ x +"]").asInt();
                        Integer itemReceiveHQ = record.get("HQ{Receive}["+ j +"]["+ x +"]").asInt();

                        if(itemReceiveId != 0) {

                            Item item =  findItemByOriginalId(items, itemReceiveId);
                            if (item != null) {
                                System.out.println("─────┬── BUY Item: " + item.getName());
                                receivable.add(new Purchasable(item, itemReceiveCount, itemReceiveHQ));
                            }


                        }

                    }

                    for(int y = 0; y < 3; y++) {
                    // fill costs
                        Integer itemReceiveId = record.get("Item{Cost}["+ j +"]["+ y +"]").asInt();
                        Integer itemReceiveCount = record.get("Count{Cost}["+ j +"]["+ y +"]").asInt();
                        Integer itemReceiveHQ = record.get("HQ{Cost}["+ j +"]["+ y +"]").asInt();
                        Integer collectabilityRating = record.get("CollectabilityRating{Cost}["+ j +"]["+ y +"]").asInt();


                        if(itemReceiveId != 0) {

                            Item item = findItemByOriginalId(items, itemReceiveId);
                            if (item != null) {
                                System.out.println("     ├── Exchange Item: " + item.getName());
                                costs.add(new Purchasable(item, itemReceiveCount, itemReceiveHQ, collectabilityRating));
                            }
                        }

                    }

                    if(!receivable.isEmpty()) {
                        purchasable.add(new SpecialShopPurchasable(receivable, costs));
                    }
                }

                if(name == null) { name = "No Name";}

                System.out.println("Adding: " + originalId + " Name: " + name);
                shops.add(new SpecialShop(originalId, name, purchasable));
               // specialShopDao.getShops().save(new SpecialShop(originalId, name, purchasable));
            }
            i++;
        }

        System.out.println("TOTAL SHOPS: " + shops.size());

        specialShopDao.getShops().saveAll(shops);
        return new ImporterResult("SpecialShops", (long) shops.size());
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


    private Item findItemByOriginalId(List<Item> items, Integer originalId) {
        for(Item item : items) {
            if(item.getOriginalId().equals(originalId)) {
                return item;
            }
        }
        return null;
    }

}
