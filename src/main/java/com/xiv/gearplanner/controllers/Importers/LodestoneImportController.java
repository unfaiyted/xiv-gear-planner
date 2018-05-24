package com.xiv.gearplanner.controllers.Importers;


import com.xiv.gearplanner.exceptions.LodestoneParserException;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.models.importers.LSCharacter;
import com.xiv.gearplanner.models.importers.LSItem;
import com.xiv.gearplanner.models.inventory.Gear;
import com.xiv.gearplanner.models.inventory.GearSet;
import com.xiv.gearplanner.models.inventory.GearWithMelds;
import com.xiv.gearplanner.models.inventory.Materia;
import com.xiv.gearplanner.parser.CharacterParser;
import com.xiv.gearplanner.parser.URLS;
import com.xiv.gearplanner.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LodestoneImportController {
    private static GearService gearDao;
    private static GearSetService gearSetDao;
    private static StaticService staticDao;
    private static ItemService itemDao;

    @Autowired
    public LodestoneImportController(StaticService staticDao, GearService gearDao,  GearSetService gearSetDao,
                                     ItemService itemDao) {
        this.staticDao = staticDao;
        this.gearDao = gearDao;
        this.gearSetDao = gearSetDao;
        this.itemDao = itemDao;
    }

    @PostMapping("/api/lodestone/import/{memberId}")
    public @ResponseBody Response getCharacter(Model model, @PathVariable Long memberId) throws LodestoneParserException {

        try {
            // Member Id
            StaticMember member = staticDao.getStatics().getMember(memberId);

            System.out.println(member.toString());

            Long LodeStoneId = member.getPlayer().getLoadstoneId();

            /* Parsed Char data from lodestone*/
            CharacterParser parser = new CharacterParser(URLS.US);
            LSCharacter character = parser.getCharacterById(LodeStoneId);
            // LS current job
            String jobAbbr = character.getClassOrJob(); // current equipped job
            String assignedJobAbbr = member.getAssignedJob().getAbbr();

            // Job on Lodestone does not match assigned job.
            if (!assignedJobAbbr.equals(jobAbbr))
                return new ResponseError("display", "Lodestone does not match selected assigned job.");


            // Creates a list of melds to assign to gear
            List<GearWithMelds> gearMatches = new ArrayList<>();

            // Get Gear Matches from Loadstone
            for (LSItem item : character.getGearSet()) {
                gearMatches.add(parseGearWithMelds(item));
            }

            // Adds weapon and  materia
            gearMatches.add(parseGearWithMelds(character.getWeapon()));

            GearSet gearSet = new GearSet(member.getAssignedJob(), member.getPlayer(), gearMatches);

            gearSetDao.getGearSets().save(gearSet);
            staticDao.getStatics().updateMemberGearSet(memberId, gearSet.getId());
        } catch (NullPointerException e) {
            // Cant find members

            e.printStackTrace();
            if (memberId == null) return new ResponseError("display", "Member not found");
            return new ResponseError("display","unable to determine error");
        }
      /*  // Send Char to view Test!
        model.addAttribute("character",character);
        model.addAttribute("player",member.getPlayer());
        model.addAttribute("gearMatches", gearMatches);*/

      return new Response(true);
    }



    private GearWithMelds parseGearWithMelds(LSItem item) {

        // Adding materia and gear from lodeston to match gear
        List<Materia> materias = new ArrayList<>();
        Gear gear = gearDao.getGears().findGearByName(item.getName());

        for(String materiaName : item.getMateria()) {
            System.out.println(materiaName);
            materias.add(itemDao.getItems().getMateriaByName(materiaName));

        }
        ;

        return new GearWithMelds(gear, materias);

    }


}
