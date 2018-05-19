package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.models.inventory.*;
import com.xiv.gearplanner.services.GearService;
import com.xiv.gearplanner.services.ItemService;
import com.xiv.gearplanner.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    List<GearSlot> gearSlotList = new ArrayList<>();

    private String directory = "src/main/resources/static/data/";

    @Autowired
    public ItemImportController(ItemService itemDao, JobService jobDao, GearService gearDao) {
        this.itemDao = itemDao;
        this.jobDao = jobDao;
        this.gearDao = gearDao;
    }

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
        data.put("EquipSlotCategory", getFileAsJson(directory +"other/EquipSlotCategory.json"));

        // Item Data
        data.put("ItemCategory", getFileAsJson(directory +"item/ItemSearchCategory.json"));
        data.put("ItemFood",  getFileAsJson(directory +"item/ItemFood.json"));
        data.put("Materia",  getFileAsJson(directory +"item/Materia.json"));
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


        i = 0; // Import Gear Equip Category
        for (JsonNode record : data.get("EquipSlotCategory")) {
            //Add to array
            Integer importId = record.get("#").asInt();


                Iterator<Map.Entry<String, JsonNode>> fields = record.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();

                    String slotName = entry.getKey();
                    if (i == 0) {
                        // add key to gearSlot list
                        if(gearDao.getGears().getGearSlotByName(slotName) == null) {
                            gearDao.getGears().addGearSlot(slotName);
                        }
                    }

                    if (i >= 1) {

                        if(gearDao.getGears().getGearSlotCategoryByOriginalId(importId) == null) {
                            // Create Slot Category
                            System.out.println(entry.getKey() + ":" + entry.getValue());
                            if (entry.getValue().asInt() == 1) {
                                // add gear slot
                                GearSlot slot = gearDao.getGears().getGearSlotByName(slotName);
                                gearDao.getGears().addGearSlotCategory(slot.getId(), importId);
                            }
                            if (entry.getValue().asInt() == -1) {
                                // disabled slots if any
                               // TODO: add this caclulation. Needs to be after creation possibly
                            }
                        }
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

                if(itemDao.getItems().findCategoryByOriginalId(importId) == null) {

                    Job job = jobDao.getJobs().findFirstByOriginalId(jobOriginalId);
                    // Get Job
                    ItemCategory cat = new ItemCategory(name, importId, icon, order, job);

                    Long parentId = null;
                    // Get subcategory
                    if (parentOriginalId != 0) {
                        ItemCategory parent = itemDao.getItems().findCategoryByOriginalId(parentOriginalId);
                        parentId = parent.getId();
                    }

                    itemDao.getItems().addCategory(name, order, icon, importId, job.getId(), parentId);
                    itemCatList.add(cat);
                }

            }
            i++;

        }



        // IMPORT ITEMS

        List<Integer> existingItemIds = itemDao.getItems().getAllByOriginalId();
        List<ItemCategory> itemCategories = itemDao.getItems().getAllCategories();

        i = 0; // Import Items
        for (JsonNode record : data.get("Item")) {

            //Add to array
            if (i >= 1) {

                Integer importId = record.get("#").asInt();
                String  name        = record.get("Name").asText();


                if(existingItemIds.contains(importId)) {
                    System.out.println("====ALREADY ADDED==== " + name);
                }

                // already imported excluded
                if(!existingItemIds.contains(importId)) {

                // Check if in categorys we want to import
                Integer categoryId = record.get("ItemSearchCategory").asInt();

                ItemCategory[] cat = { null };

                itemCategories.forEach(itemCategory -> {
                    if(itemCategory.getOriginalId().equals(categoryId)) cat[0] = itemCategory;
                });

                       // itemDao.getItems().findCategoryByOriginalId(categoryId);
                Long parentCatId = (cat[0].getParent() != null) ? cat[0].getParent().getId() : 0;


                    String  desc        = record.get("Description").asText();
                    Integer icon        = record.get("Icon").asInt();
                    Integer itemLevel  = record.get("Level{Item}").asInt();
                    Integer equipLevel = record.get("Level{Equip}").asInt();
                    Integer slotCategoryOriginalId = record.get("EquipSlotCategory").asInt();
                    Integer jobUseOriginalId = record.get("ClassJob{Use}").asInt();
                    String advancedMelding  = record.get("IsAdvancedMeldingPermitted").asText();

                    Boolean advMeldingAsBoolean = (advancedMelding.equals("True"));

                    Integer materiaSlotCount = record.get("MateriaSlotCount").asInt();
                   // Integer equipRestriction = record.get("EquipRestriction").asInt();
                    Integer jobCatOriginalId = record.get("ClassJobCategory").asInt();


                    //Gear Importing
                    // 57 = materia orig id

                        // Create a new
                        GearSlotCategory slot = gearDao.getGears().getGearSlotCategoryByOriginalId(slotCategoryOriginalId);

                        if (slot == null) {
                           // System.out.println("====SKIPPING==== " + name);
                        }

                        if (slot != null || cat[0].getName().equalsIgnoreCase("Materia") ||
                                cat[0].getName().equalsIgnoreCase("Meals") ||
                                cat[0].getName().equalsIgnoreCase("Seafood") ) {

                            Job useJob = jobDao.getJobs().findFirstByOriginalId(jobUseOriginalId);
                            GearEquipCategory equipCategory = gearDao.getEquipCategories().findFirstByOriginalId(jobCatOriginalId);

                            // Materia
                            if (cat[0].getName().equalsIgnoreCase("Materia")) {

                                System.out.println("====MATERIA==== " + name);


                                Materia materia = new Materia(
                                        name, desc, itemLevel, icon, importId, cat[0]);


                                itemDao.getItems().save(materia);

                                // Gear
                            }else if(cat[0].getName().equalsIgnoreCase("Meals") || cat[0].getName().equalsIgnoreCase("Seafood")) {

                                System.out.println("====MEALS (FOOOD)==== " + name);

                                Food food = new Food(name, desc,itemLevel, icon, importId,cat[0]);

                                itemDao.getItems().save(food);


                            } else {
                                System.out.println("====GEAR==== " + name);

                                //{"#":"354","Singular":"dated steel hatchet","field3":"0",
                                // "Plural":"dated steel hatchets","StartsWithVowel":"0",
                                //"Description":"",
                                // "Name":"Dated Steel Hatchet","Icon":"38102","Level{Item}":"37",
                                // "Rarity":"1","FilterGroup":"1","ItemUICategory":"30",
                                // "ItemSearchCategory":"0","EquipSlotCategory":"1",
                                // "IsDyeable":"False","IsCrestWorthy":"False","ItemAction":"0",
                                // "Cooldown<s>":"0"
                                // "Item{Glamour}":"21800","Salvage":"0","IsCollectable":"False",
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


                                List<ItemStat> stats = new ArrayList<>();

                                for (int j = 0; j <= 5; j++) {
                                    System.out.println("Adding Gear Param");
                                    Integer param = record.get("BaseParam[" + j + "]").asInt();
                                    Long paramValue = record.get("BaseParamValue[" + j + "]").asLong();

                                    GearStatType statType = gearDao.getGears().findStatTypeByOriginalId(param);
                                    stats.add(new ItemStat(statType, paramValue));

                                }

                                for (int j = 0; j <= 5; j++) {
                                    Integer param = record.get("BaseParam{Special}[" + j + "]").asInt();
                                    Integer paramValue = record.get("BaseParamValue{Special}[" + j + "]").asInt();

                                    GearStatType stat = gearDao.getGears().findStatTypeByOriginalId(param);

                                }


                                Item gear = new Gear(
                                        name, importId, desc, itemLevel, equipLevel, icon,
                                        advMeldingAsBoolean, materiaSlotCount,
                                        slot, equipCategory, useJob, cat[0], stats);

                                itemDao.getItems().save(gear);

                                // Param  are called -> gear_stat_type
                                // "BaseParam[0]":"72","BaseParamValue[0]":"70",
                                // "ItemSpecialBonus":"1",
                                // "ItemSpecialBonus{Param}":"0",
                                // "BaseParam{Special}[0]":"12",
                                // "BaseParamValue{Special}[0]":"0"
                                // ,"BaseParamValue{Special}[5]":"0","MaterializeType":"11","MateriaSlotCount":"0",
                                // "IsAdvancedMeldingPermitted":"False","IsPvP":"False"},

                            } // end gear import

                        } // gear slots


                } // already imported skip end


            } // ignore first record data type end
            i++;
        } // records loop end
        Long totalItems = (long) i;

        System.out.println("MATERIA" + data.get("Materia").size());

        i = 0; // Import Materia, convert stats
        for (JsonNode record : data.get("Materia")) {
            //Add to array

            if (i >= 1) {

                Integer row = record.get("#").asInt();
                Integer paramOriginalId = record.get("BaseParam").asInt();
                // base param = gearStatType

                if(paramOriginalId != 0) {

                    GearStatType statType = gearDao.getGears().findStatTypeByOriginalId(paramOriginalId);

                    System.out.println(statType.getName() + " = STAT TYPE");

                    for (int j = 0; j <= 9; j++) {
                        Integer item = record.get("Item[" + j + "]").asInt();
                        Long value = record.get("Value[" + j + "]").asLong();

                        if (item != 0) {

                            Item materia = itemDao.getItems().findByOriginalId(item);

                            if(materia != null) {
                                System.out.println("MATERIA FOUND: "+ materia.getName());

                              if(itemDao.getItems().findItemStatByItemId(materia.getId()) == null) {
                                    System.out.println(String.format("matid: %1$d, statType: %2$d, val: %3$d",
                                                                    materia.getId(), statType.getId(), value));

                                    itemDao.getItems().addItemStat(materia.getId(), statType.getId(), value);
                                }
                            }
                        }

                    }

                }
            }
            i++;
        }
        Long totalMateriaStats = (long) i;


        results.add(new ImporterResult("MateriaStatTypes",totalMateriaStats));
        results.add(new ImporterResult("GearStatTypes", (long) StatTypelist.size()));
        results.add(new ImporterResult("Jobs",(long) list.size()));
        results.add(new ImporterResult("GearEquipCategories",(long) gearList.size()));
        results.add(new ImporterResult( "ItemCategories", (long) itemCatList.size()));
        results.add(new ImporterResult("Items",totalItems));

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
