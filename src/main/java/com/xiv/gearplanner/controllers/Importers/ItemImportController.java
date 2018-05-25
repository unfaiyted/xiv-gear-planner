package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.models.importers.DataItem;
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

    @GetMapping("/import/data/items")
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
        data.put("ItemCategory", getFileAsJson(directory +"additional/ItemSearchCategory.csv.json"));
        data.put("ItemFood",  getFileAsJson(directory +"additional/ItemFood.csv.json"));
        data.put("Materia",  getFileAsJson(directory +"additional/Materia.csv.json"));
        data.put("Item",  getFileAsJson(directory +"additional/Item.csv.json"));

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

        // Import Items logic
        results.add(importItems(data.get("Item")));


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

        // executs update materia query to add colors to materia directly
        itemDao.getItems().updateMateriaColors();

        // Checks materia and links the stat_id to the item stat table
        itemDao.getItems().matchStatsToMateria();

        Long totalMateriaStats = (long) i;


        results.add(new ImporterResult("MateriaStatTypes",totalMateriaStats));
        results.add(new ImporterResult("GearStatTypes", (long) StatTypelist.size()));
        results.add(new ImporterResult("Jobs",(long) list.size()));
        results.add(new ImporterResult("GearEquipCategories",(long) gearList.size()));
        results.add(new ImporterResult( "ItemCategories", (long) itemCatList.size()));

        model.addAttribute("results", results);
        return "/import/results";
    }



    /*
    *       ITEM IMPORT FUNCTIONS
    *
    *
    *      ---------------------
    * */

    private ImporterResult importItems(JsonNode data) {
        List<ImporterResult> results = new ArrayList<>();
        List<Integer> existingItemIds = itemDao.getItems().getAllByOriginalId();
        List<ItemCategory> itemCategories = itemDao.getItems().getAllCategories();

        int i = 0; // Import Items
        for (JsonNode record : data) {

            //Add to array
            if (i >= 1) {

                DataItem item = recordToItem(record);

                if (existingItemIds.contains(item.getIcon())) {
                    System.out.println("====SKIPPING ALREADY ADDED==== " + item.getName());
                    continue; // next iteration
                }

                // Check if in categories we want to import

                ItemCategory cat = getCategory(item.getOriginalCategoryId(), itemCategories);


                if (item.getSlotCategoryOriginalId() != 0) {
                    // Check if Gear
                    importItemGear(item, cat);
                } else if (cat.getName().equalsIgnoreCase("Meals") ||
                           cat.getName().equalsIgnoreCase("Seafood")) {
                    // Check if Food
                    importItemFood(item, cat);
                } else if (cat.getName().equalsIgnoreCase("Materia")) {
                    // Check if Materia
                    importItemMateria(item, cat);

                } else if(cat.getName().equalsIgnoreCase("Materials") ||
                        (cat.getParent() != null && cat.getParent().getName().equalsIgnoreCase("Materials"))) {
                    // Check if Material
                    importItemMaterial(item, cat);
                } else {
                    // Check if Other
                    importItemOther(item, cat);
                }

            }
            i++;
        }

        return new ImporterResult("Items",(long) i);
    }


    private void importItemGear(DataItem data, ItemCategory cat) {
        // Create a new
        GearSlotCategory slot =
                gearDao.getGears().getGearSlotCategoryByOriginalId(data.getSlotCategoryOriginalId());

        Job useJob = jobDao.getJobs().findFirstByOriginalId(data.getJobUseOriginalId());
        GearEquipCategory equipCategory = gearDao.getEquipCategories().findFirstByOriginalId(data.getJobUseOriginalId());

        System.out.println("====GEAR==== " + data.getName());


        List<ItemStat> stats = new ArrayList<>();

        System.out.println("Adding Gear Param");
        for (Map.Entry<Integer, Long> param : data.getParam().entrySet()) {
            Integer key = param.getKey();
            Long value = param.getValue();

            GearStatType statType = gearDao.getGears().findStatTypeByOriginalId(key);
            stats.add(new ItemStat(statType, value));

        }

        System.out.println("Adding Special Gear Param");
        for (Map.Entry<Integer, Long> param : data.getParamSpecial().entrySet()) {
            Integer key = param.getKey();
            Long value = param.getValue();


            // TODO: Implement and figure out where these stats show
            //GearStatType statType = gearDao.getGears().findStatTypeByOriginalId(key);
            //stats.add(new ItemStat(statType, value));

        }


        Item gear = new Gear(
        data.getName(), data.getImportId(), data.getDesc(), data.getItemLevel(),
                data.getEquipLevel(), data.getIcon(), data.getAdvMeldingAsBoolean(), data.getMateriaSlotCount(),
        slot, equipCategory, useJob, cat, stats);

        itemDao.getItems().save(gear);


        }

    private  void importItemMateria(DataItem data, ItemCategory cat) {

        System.out.println("====MATERIA==== " + data.getName());

        Materia materia = new Materia(
        data.getName(), data.getDesc(), data.getItemLevel(), data.getIcon(),
                data.getImportId(), cat);

        itemDao.getItems().save(materia);

        }

    private void importItemFood(DataItem data, ItemCategory cat) {

        System.out.println("====MEALS (FOOD)==== " + data.getName());

        Food food = new Food( data.getName(), data.getDesc(), data.getItemLevel(),
                data.getIcon(), data.getImportId(), cat);

        itemDao.getItems().save(food);


        }

    private void importItemOther(DataItem data, ItemCategory cat) {

        System.out.println("====IMPORT OTHER==== " + data.getName());

        Other other = new Other( data.getName(), data.getDesc(), data.getItemLevel(),
                data.getIcon(), data.getImportId(), cat);

        itemDao.getItems().save(other);


    }

    private void importItemMaterial(DataItem data, ItemCategory cat) {

        System.out.println("====IMPORT MATERIAL==== " + data.getName());

        Material material = new Material( data.getName(), data.getDesc(), data.getItemLevel(),
                data.getIcon(), data.getImportId(), cat);

        itemDao.getItems().save(material);


    }

    private DataItem recordToItem(JsonNode record) {

        Integer importId    = record.get("#").asInt();
        String  name        = record.get("Name").asText();
        String  desc        = record.get("Description").asText();
        Integer icon        = record.get("Icon").asInt();
        Integer itemLevel   = record.get("Level{Item}").asInt();
        Integer equipLevel  = record.get("Level{Equip}").asInt();
        Integer slotCategoryOriginalId = record.get("EquipSlotCategory").asInt();
        Integer jobUseOriginalId = record.get("ClassJob{Use}").asInt();
        String  advancedMelding  = record.get("IsAdvancedMeldingPermitted").asText();
        Boolean advMeldingAsBoolean = (advancedMelding.equals("True"));
        Integer materiaSlotCount = record.get("MateriaSlotCount").asInt();
        Integer jobCatOriginalId = record.get("ClassJobCategory").asInt();
        Double  blockRate = record.get("BlockRate").asDouble();
        Double  block = record.get("Block").asDouble();
        Double  defensePhys = record.get("Defense{Phys}").asDouble();
        Double  defenseMag = record.get("Defense{Mag}").asDouble();
        Double  damagePhys = record.get("Damage{Phys}").asDouble();
        Double  damageMag = record.get("Damage{Phys}").asDouble();
        Integer originalCategoryId = record.get("ItemSearchCategory").asInt();

        Long delay = record.get("Delay<ms>").asLong();

        Map<Integer, Long> param = new HashMap<Integer, Long>();
        Map<Integer, Long> paramSpecial = new HashMap<Integer, Long>();

        for (int j = 0; j <= 5; j++) {
            Integer baseParam = record.get("BaseParam[" + j + "]").asInt();
            Long  baseParamValue = record.get("BaseParamValue[" + j + "]").asLong();
            param.put(baseParam, baseParamValue);
        }

        for (int j = 0; j <= 5; j++) {
            Integer specialParam = record.get("BaseParam{Special}[" + j + "]").asInt();
            Long specialParamValue = record.get("BaseParamValue{Special}[" + j + "]").asLong();
            paramSpecial.put(specialParam, specialParamValue);
        }

        return new DataItem( desc,  icon,  itemLevel,  equipLevel,  slotCategoryOriginalId,
                 jobUseOriginalId,  advancedMelding,  advMeldingAsBoolean,  materiaSlotCount,
                jobCatOriginalId,  originalCategoryId,  importId,  name,  blockRate,  block,
                defensePhys,  defenseMag,  damagePhys,  damageMag,  delay, param, paramSpecial);

    }


    private ItemCategory getCategory(Integer originalCategoryId, List<ItemCategory> itemCategories) {

        ItemCategory[] cat = { null };

        itemCategories.forEach(itemCategory -> {
            if(itemCategory.getOriginalId().equals(originalCategoryId)) cat[0] = itemCategory;
        });

        return cat[0];

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
