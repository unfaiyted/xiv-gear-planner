package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.Gear;
import com.xiv.gearplanner.models.GearEquipCategory;
import com.xiv.gearplanner.models.Item;
import com.xiv.gearplanner.models.Job;
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
    private String directory = "src/main/resources/static/data/";
    EntityManager entityManager;

    @Autowired
    public ItemImportController(ItemService itemDao, JobService jobDao) {
        this.itemDao = itemDao;
        this.jobDao = jobDao;
    }
    @GetMapping("/import/items")
    public String ReadJson(Model model) throws IOException {
        objectMapper = new ObjectMapper();
        System.out.println("=====OPENING FILE=====");
        data = objectMapper.readTree(new File("src/main/resources/static/data/item.json"));

        List<Item> items = new ArrayList<>();

        System.out.println("=====LOOPING=====");
            for (JsonNode item : data) {

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
                   // items.add(new Item(name,  icon,  iLvl,  equipLevel,  xivdbID,  lodestoneId));
               }
        }

        System.out.println("=====SAVING=====ITEMS====" + items.size() + "TOTAL ====");
        itemDao.getItems().saveAll(items);

        System.out.println("===RENDER VIEW===");
        model.addAttribute("itemCount", items.size());
        return "import/items";
    }

    @GetMapping("/import/all/test")
    public String  importJobs(Model model) throws IOException {

        System.out.println("====BEGIN DATA IMPORT======");

        HashMap<String, JsonNode> data = new HashMap<String, JsonNode>();

        data.put("Job", getFileAsJson(directory +"other/ClassJob.json"));
        data.put("GearEquipCategory", getFileAsJson(directory +"other/ClassJobCategory.json"));
        data.put("StatType", getFileAsJson(directory +"other/BaseParam.json"));

        // Item Data
        data.put("ItemCategory", getFileAsJson(directory +"item/ItemSearchCategory.json"));
        data.put("ItemFood",  getFileAsJson(directory +"item/ItemFood.json"));

        System.out.println("=====FILE GATHERING COMPLETE ===");


        // Job Data
        List<Job> list = new ArrayList<>();

        System.out.println("=====LOOP ADD ALL JOBS=====");

        int i = 0; // skip first row
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



        i = 0; // skip first row
        for (JsonNode record : data.get("GearEquipCategory")) {
            //Add to array
            if (i >= 1) {
                //{"#":"41","Name":"PGL MNK","ADV":"False","GLA":"False","PGL":"True","MRD":"False","LNC":"False","ARC":"False","CNJ":"False","THM":"False","CRP":"False","BSM":"False","ARM":"False","GSM":"False","LTW":"False","WVR":"False","ALC":"False","CUL":"False","MIN":"False","BTN":"False","FSH":"False","PLD":"False","MNK":"True","WAR":"False","DRG":"False","BRD":"False","WHM":"False","BLM":"False","ACN":"False","SMN":"False","SCH":"False","ROG":"False","NIN":"False","MCH":"False","DRK":"False","AST":"False","field37":"False","field38":"False"},
                Integer importId = record.get("#").asInt();
                String name = record.get("Name").asText();

                GearEquipCategory category = new GearEquipCategory(name, importId);

                Iterator<Map.Entry<String, JsonNode>> fields = record.fields();

                while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        System.out.println(entry.getKey() + ":" + entry.getValue());

                        if(entry.getValue().asBoolean()) {
                            Job job = jobDao.getJobs().findJobByAbbr(entry.getKey());
                            category.addJob(job);
                        }

                }

            }
            i++;
        }



        //jobDao.getJobs().saveAll(list);
        //save to database
        model.addAttribute("results",list.size());
        return "/import/results";
    }

    ////////////////////////////////////
    ////  1,"Primary Arms",60102,0,0,0
    ////  2,"Primary Tools",60113,0,0,0
    ////  3,"Primary Tools",60120,0,0,0
    ////  4,"Armor",60126,0,0,0
    ////  5,"Accessories",60135,0,0,0
    //// ID or Category IN (1,5)
    ////  6,"Medicines",60136,0,0,0
    ////  7,"Materials",60137,0,0,0
    ////  8,"Other",60159,0,0,0

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
