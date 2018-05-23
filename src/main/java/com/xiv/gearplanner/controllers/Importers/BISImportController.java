package com.xiv.gearplanner.controllers.Importers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.xiv.gearplanner.exceptions.AriyalaParserException;
import com.xiv.gearplanner.exceptions.UnexpectedHtmlStructureException;
import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobBIS;
import com.xiv.gearplanner.models.importers.AriyalaBIS;
import com.xiv.gearplanner.models.importers.AriyalaItem;
import com.xiv.gearplanner.models.inventory.*;
import com.xiv.gearplanner.parser.AriyalaBISParser;
import com.xiv.gearplanner.parser.URLS;
import com.xiv.gearplanner.services.GearService;
import com.xiv.gearplanner.services.ItemService;
import com.xiv.gearplanner.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BISImportController {
   private JobService jobDao;
   private GearService gearDao;
   private ItemService itemDao;

   @Value("${chrome-driver-install-location}")
   String driverLocation;

   @Autowired
    public BISImportController(JobService jobDao, GearService gearDao, ItemService itemDao) {
        this.jobDao = jobDao;
        this.gearDao = gearDao;
        this.itemDao = itemDao;
    }

    @GetMapping("/bis/importer")
    public String importBIS(Model model) {
        return "import/bis";
    }


    // Verify Page
    @PostMapping("/bis/importer")
    public String showBIS(Model model, @RequestParam(name = "bis") String id, @RequestParam(name = "name") String name) throws AriyalaParserException {

        System.setProperty("webdriver.chrome.driver", driverLocation);

        try {
            AriyalaBISParser parser = new AriyalaBISParser(URLS.BIS);
            AriyalaBIS ariyalaBIS = parser.getBISbyId(id);
            if(ariyalaBIS.getItems().isEmpty()) {
                model.addAttribute("error","Problem importing from ariyala. (Possible bad Code)");
            }

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(ariyalaBIS);

            model.addAttribute("name", name);
            model.addAttribute("bisStr",json);
            model.addAttribute("bis", ariyalaBIS);

        } catch(UnexpectedHtmlStructureException e) {
            model.addAttribute("error","Problem importing from ariyala. (HTML Issue)");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("displayResult",true);
        return "import/bis";
    }

    @PostMapping("/bis/importer/save")
    public String saveBIS(Model model, @RequestParam(name ="bisJson") String json,
                          @RequestParam(name="bis-name-submit") String name) throws IOException {
        // get bis object from page
        ObjectMapper mapper = new ObjectMapper();
        AriyalaBIS bis = mapper.readValue(json, AriyalaBIS.class);

        Job job =  jobDao.getJobs().findJobByAbbr(bis.getClassAbbr());


        List<GearWithMelds> gearWithMelds = new ArrayList<>();

        Food food = null;

        // Loop for items ariyala
        for(AriyalaItem item: bis.getItems()) {

            System.out.println(item.getName() + item.getId());
            Gear gear = gearDao.getGears().getFirstByOriginalId(item.getId().intValue());

            if(gear == null) {
                food = itemDao.getItems().findFoodByOriginalId(item.getId().intValue());
            }

            if(gear != null) {
                List<Meld> melds = new ArrayList<>();

                // Adding melds to list of melds for gear.
                for (Map.Entry<Integer, String[]> me : item.getMateriaSlot().entrySet()) {
                    String[] val = me.getValue();
                    melds.add(new Meld(materiaFromString(val[0])));
                    // val[1]; //color
                }

                gearWithMelds.add(new GearWithMelds(gear, melds));

            }

        }

        JobBIS jobBIS = new JobBIS(bis.getId(),name,job);
        jobBIS.setMelded(gearWithMelds);

        if(food != null) {
            jobBIS.setFood(food);
        }

        jobDao.getSets().save(jobBIS);

        // save bis to db
        model.addAttribute("json",json);
        return "import/bisSaved";
    }



    Materia materiaFromString(String statString) {

        String pattern = "^([A-Za-z ]+).([0-9]+)";


        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(statString);

        System.out.println(statString);

        if (m.find()) {
            System.out.println("Found value: " + m.group(1)); //word
            System.out.println("Found value: " + m.group(2)); //value

           Materia materia =itemDao.getItems()
                   .getMateriaByStat(m.group(1).trim(),Long.parseLong(m.group(2)));

            System.out.println(materia.toString());

            return materia;
        }

        return null;
    }




}