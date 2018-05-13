package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.repositories.GearEquipCategories;
import com.xiv.gearplanner.services.GearService;
import com.xiv.gearplanner.services.ItemService;
import com.xiv.gearplanner.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
*
* Initially got the data from the ffxiv repository and converted it to json stripping out the first line
* removing the key ids,
* 1st record has datatypes by column.
* */

@Controller
public class ItemImportController {
    private JsonNode data;

    private ObjectMapper objectMapper;
    private ItemService itemDao;
    private JobService jobDao;
    private GearService gearDao;

    // Create Lists of imports
    List<Job> list = new ArrayList<>();
    List<GearEquipCategory> gearList = new ArrayList<>();
    List<GearStatType> StatTypelist = new ArrayList<>();
    List<ItemCategory> itemCatList = new ArrayList<>();

    private String directory = "src/main/resources/static/data/";

    @Autowired
    public ItemImportController(ItemService itemDao, JobService jobDao, GearService gearDao) {
        this.itemDao = itemDao;
        this.jobDao = jobDao;
        this.gearDao = gearDao;
    }

//    @GetMapping("/import/items")
//    public String ReadJson(Model model) throws IOException {
//        objectMapper = new ObjectMapper();
//        System.out.println("=====OPENING FILE=====");
//        data = objectMapper.readTree(new File("src/main/resources/static/data/item.json"));
//
//        List<Item> items = new ArrayList<>();
//
//        System.out.println("=====LOOPING=====");
//            for (JsonNode item : data) {
//
//            System.out.println("==ADDING ITEM===");
//               if(item != null) {
//                   String name = item.get("name").asText();
//                    System.out.print(name);
//                    Integer iLvl = Integer.parseInt(item.get("level_item").asText());
//                    System.out.print(iLvl);
//                    Integer equipLevel = Integer.parseInt(item.get("level_equip").asText());
//                    System.out.print(equipLevel);
//                    Long xivdbID = item.get("id").asLong();
//                    String icon = item.get("icon").asText();
//                    String lodestoneId = (item.get("lodestone_id").asText() != null) ? item.get("lodestone_id").asText() : "" ;
//                   // items.add(new Item(name,  icon,  iLvl,  equipLevel,  xivdbID,  lodestoneId));
//               }
//        }
//
//        System.out.println("=====SAVING=====ITEMS====" + items.size() + "TOTAL ====");
//        itemDao.getItems().saveAll(items);
//
//        System.out.println("===RENDER VIEW===");
//        model.addAttribute("itemCount", items.size());
//        return "import/items";
//    }

    @GetMapping("/import/all/test")
    public String  importJobs(Model model) throws IOException {
        int i = 0; // skip first row

        // Store results from imports
        List<ImporterResult> results = new ArrayList<>();

        HashMap<String, JsonNode> data = new HashMap<String, JsonNode>();

        // Base Data
        data.put("Job", getFileAsJson(directory +"other/ClassJob.json"));
        data.put("GearEquipCategory", getFileAsJson(directory +"other/ClassJobCategory.json"));
        data.put("StatType", getFileAsJson(directory +"other/BaseParam.json"));

        // Item Data
        data.put("ItemCategory", getFileAsJson(directory +"item/ItemSearchCategory.json"));
        data.put("ItemFood",  getFileAsJson(directory +"item/ItemFood.json"));
        data.put("Item",  getFileAsJson(directory +"item/Item.json"));

        System.out.println("=====FILE GATHERING COMPLETE ===");

        // Job Importing
        for (JsonNode record : data.get("Job")) {
            //Add to array
            if (i >= 1) {
                Integer importId = record.get("#").asInt();
                String name = record.get("Name{English}").asText();
                String abbr = record.get("Abbreviation").asText();
                Integer parentJobOrigId = record.get("ClassJob{Parent}").asInt();

                //Prevent loading if already exists in DB
                if(jobDao.getJobs().findFirstByOriginalId(importId) == null) {

                    Job job = new Job(name, abbr, importId);
                    jobDao.save(job);
                    Job parent = jobDao.getJobs().findFirstByOriginalId(parentJobOrigId);
                    jobDao.getJobs().updateParentJob(job.getId(), parent.getId());

                    list.add(job);
                }
            }
            i++;
        }

        i = 0; // Import Gear Equip Category
        for (JsonNode record : data.get("GearEquipCategory")) {
            //Add to array
            if (i >= 1) {
                Integer importId = record.get("#").asInt();
                String name = record.get("Name").asText();

                if (gearDao.getEquipCategories().findFirstByOriginalId(importId) == null) {

                    GearEquipCategory category = new GearEquipCategory(name, importId);
                    Iterator<Map.Entry<String, JsonNode>> fields = record.fields();

                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();

                        if (entry.getValue().asText().equals("True")) {
                            if (jobDao.getJobs().findJobByAbbr(entry.getKey()) != null) {
                                Job job = jobDao.getJobs().findJobByAbbr(entry.getKey());
                                System.out.println(entry.getKey() + ":" + entry.getValue());
                                category.addJob(job);
                            }
                        }
                    }

                    gearList.add(category);
                    gearDao.getEquipCategories().save(category);
                }
            }
            i++;

        }

        i = 0; // Import Gear Stat Types
        for (JsonNode record : data.get("StatType")) {
                //Add to array
                if (i >= 1) {

                    Integer importId = record.get("#").asInt();
                    String name = record.get("Name").asText();
                    String desc = record.get("Description").asText();

                    //Prevent loading if already exists in DB
                    if (gearDao.getGears().findStatTypeByOriginalId(importId) == null) {

                        GearStatType stat = new GearStatType(importId, name, desc);
                        gearDao.getGears().addGearStat(name, desc, importId);
                        StatTypelist.add(stat);

                    }
                }
                i++;

        }

        i = 0; // Import Item Categories
        for (JsonNode record : data.get("ItemCategory")) {
            //Add to array
            if (i >= 1) {

                Integer importId = record.get("#").asInt();
                String name = record.get("Name").asText();
                Integer icon = record.get("Icon").asInt();
                Integer order = record.get("Order").asInt();
                Integer parentOriginalId = record.get("Category").asInt();
                Integer jobOriginalId = record.get("ClassJob").asInt();

                if(itemDao.getItems().findCategoryByOriginalId((long) importId) == null) {

                    Job job = jobDao.getJobs().findFirstByOriginalId(jobOriginalId);
                    // Get Job
                    ItemCategory cat = new ItemCategory(name, (long) importId, icon, order, job);

                    Long parentId = null;
                    // Get subcategory
                    if (parentOriginalId != 0) {
                        ItemCategory parent = itemDao.getItems().findCategoryByOriginalId((long) parentOriginalId);
                        parentId = parent.getId();
                    }

                    itemDao.getItems().addCategory(name, order, icon, (long) importId, job.getId(), parentId);
                    itemCatList.add(cat);
                }

            }
            i++;

        }


        ////////////////////////////////////
        ////2  1,"Primary Arms",60102,0,0,0
        ////3  2,"Primary Tools",60113,0,0,0
        ////4  3,"Primary Tools",60120,0,0,0
        ////5  4,"Armor",60126,0,0,0
        ////6  5,"Accessories",60135,0,0,0
        //// ID or Category IN (1,5)
        ////  6,"Medicines",60136,0,0,0
        ////  7,"Materials",60137,0,0,0
        ////  8,"Other",60159,0,0,0

        i = 0; // Import Item Categories
        for (JsonNode record : data.get("Item")) {
            //Add to array
            if (i >= 1) {

                Long categoryId = record.get("ItemSearchCategory").asLong();

                ItemCategory cat = itemDao.getItems().findCategoryByOriginalId(categoryId);

                Long parentCatId = cat.getParent().getId();

                if(parentCatId == null || parentCatId == 2L ||
                        parentCatId == 3L || parentCatId == 4L ||
                        parentCatId == 5L || parentCatId == 6L
                        )
                {

                //{"#":"354","Singular":"dated steel hatchet","field3":"0",
                // "Plural":"dated steel hatchets","StartsWithVowel":"0",
                //"Description":"",
                // "Name":"Dated Steel Hatchet","Icon":"38102","Level{Item}":"37",
                // "Rarity":"1","FilterGroup":"1","ItemUICategory":"30",
                // "ItemSearchCategory":"0","EquipSlotCategory":"1",
                // "StackSize":"1","IsUnique":"False","IsUntradable":"True","IsIndisposable":"False"
                // ,"Price{Mid}":"4357","Price{Low}":"66","CanBeHq":"True",
                // "IsDyeable":"False","IsCrestWorthy":"False","ItemAction":"0",,
                // "Cooldown<s>":"0"
                // "Item{Glamour}":"21800","Salvage":"0","IsCollectable":"False",
                // "Level{Equip}":"37",
                // "EquipRestriction":"1","ClassJobCategory":"18",
                    // "BaseParamModifier":"9","Model{Main}":"7101, 1, 3, 0",
                // "Model{Sub}":"0, 0, 0, 0","ClassJob{Use}":"17"
                    // "Damage{Phys}":"12" ,"Damage{Mag}":"12",
                    // "Delay<ms>":"3200","BlockRate":"0","Block":"0"
                    // "Defense{Phys}":"0","Defense{Mag}":"0",

                    Double blockRate = record.get("BlockRate").asDouble();
                    Double block = record.get("Block").asDouble();
                    Double defensePhys = record.get("Defense{Phys}").asDouble();
                    Double defenseMag = record.get("Defense{Mag}").asDouble();
                    Double damagePhys = record.get("Damage{Phys}").asDouble();
                    Double damageMag = record.get("Damage{Phys}").asDouble();

                    Long delay = record.get("Delay<ms>").asLong();


                    for(int j=0; j <= 5; j++) {
                        Integer param = record.get("BaseParam[" + j + "]").asInt();
                        Integer paramValue = record.get("BaseParamValue[" + j + "]").asInt();

                        GearStatType stat = gearDao.getGears().findStatTypeByOriginalId(param);

                    }

                    for(int j=0; j <= 5; j++) {
                        Integer param = record.get("BaseParam{Special}[" + j + "]").asInt();
                        Integer paramValue =record.get("BaseParamValue{Special}[" + j + "]").asInt();

                        GearStatType stat = gearDao.getGears().findStatTypeByOriginalId(param);

                    }

                    // Param  are called -> gear_stat_type
                // "BaseParam[0]":"72","BaseParamValue[0]":"70",
                // "BaseParam[1]":"73","BaseParamValue[1]":"40",
                    // "BaseParam[2]":"0","BaseParamValue[2]":"0",
                // "BaseParam[3]":"0","BaseParamValue[3]":"0",
                    // "BaseParam[4]":"0","BaseParamValue[4]":"0",
                // "BaseParam[5]":"0","BaseParamValue[5]":"0",
                    // "ItemSpecialBonus":"1",
                // "ItemSpecialBonus{Param}":"0",
                    // "BaseParam{Special}[0]":"12",
                    // "BaseParamValue{Special}[0]":"0"
                // ,"BaseParam{Special}[1]":"13","BaseParamValue{Special}[1]":"0"
                    // "BaseParam{Special}[2]":"72"
                // ,"BaseParamValue{Special}[2]":"9","BaseParam{Special}[3]":"73",
                    // "BaseParamValue{Special}[3]":"5"
                // ,"BaseParam{Special}[4]":"0","BaseParamValue{Special}[4]":"0",
                    // "BaseParam{Special}[5]":"0"
                // ,"BaseParamValue{Special}[5]":"0","MaterializeType":"11","MateriaSlotCount":"0",
                // "IsAdvancedMeldingPermitted":"False","IsPvP":"False"},

                    Integer importId    = record.get("#").asInt();
                    String  name        = record.get("Name").asText();
                    String  desc        = record.get("Description").asText();
                    Integer icon        = record.get("Icon").asInt();
                    Integer order       = record.get("Order").asInt();
                    Integer item_level  = record.get("Level{Item}").asInt();
                    Integer equip_level = record.get("Level{Equip}").asInt();
                    Integer slotCategoryOriginalId = record.get("EquipSlotCategory").asInt();
                    Integer jobCatOriginalId = record.get("ClassJob Category").asInt();
                    Integer jobUseOriginalId = record.get("ClassJob{Use}").asInt();
                    Boolean advancedMelding  = record.get("IsAdvancedMeldingPermitted").asBoolean();
                    Integer materiaSlotCount = record.get("MateriaSlotCount").asInt();




                }




            }
            i++;
        }


        results.add(new ImporterResult("GearStatTypes", (long) StatTypelist.size()));
        results.add(new ImporterResult("Jobs",(long) list.size()));
        results.add(new ImporterResult("GearEquipCategories",(long) gearList.size()));
        results.add(new ImporterResult( "ItemCategories", (long) itemCatList.size()));

        model.addAttribute("results", results);
        return "/import/results";
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
