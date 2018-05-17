package com.xiv.gearplanner.controllers.Importers;

import com.xiv.gearplanner.exceptions.AriyalaParserException;
import com.xiv.gearplanner.exceptions.UnexpectedHtmlStructureException;
import com.xiv.gearplanner.models.Response;
import com.xiv.gearplanner.models.ResponseError;
import com.xiv.gearplanner.models.importers.AriyalaBIS;
import com.xiv.gearplanner.parser.AriyalaBISParser;
import com.xiv.gearplanner.parser.URLS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BISImportController {

    @GetMapping("/bis/importer")
    public String importBIS(Model model) {
        return "import/bis";
    }

    @PostMapping("/bis/importer")
    public String showBIS(Model model, @RequestParam(name = "bis") String id) throws AriyalaParserException {

        try {

            AriyalaBISParser parser = new AriyalaBISParser(URLS.BIS);
            AriyalaBIS ariyalaBIS = parser.getBISbyId(id);
            model.addAttribute("bis",ariyalaBIS);
        } catch(UnexpectedHtmlStructureException e) {
            model.addAttribute("error","Problem importing from ariyala. (HTML Issue)");
        }

        model.addAttribute("displayResult",true);
        return "import/bis";
    }

    @GetMapping("/api/bis/import/{bisId}")
    public @ResponseBody
    Response getBISData(Model model, @PathVariable String bisId) throws AriyalaParserException {

        try {
            /* Parsed Char data from lodestone*/
            AriyalaBISParser parser = new AriyalaBISParser(URLS.BIS);
            AriyalaBIS bis = parser.getBISbyId(bisId);

            // Add BIS data to database

        } catch (NullPointerException e) {
            return new ResponseError("display", "Error importing BIS from Ariyala");
        } catch (UnexpectedHtmlStructureException e) {
            e.printStackTrace();
        }

        return new Response(true);
    }

}