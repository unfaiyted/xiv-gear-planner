package com.xiv.gearplanner.controllers.Importers;


import com.xiv.gearplanner.exceptions.LodestoneParserException;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.models.importers.LSCharacter;
import com.xiv.gearplanner.models.importers.LSItem;
import com.xiv.gearplanner.models.inventory.Gear;
import com.xiv.gearplanner.models.inventory.GearSet;
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

    @Autowired
    public LodestoneImportController(StaticService staticDao, GearService gearDao,  GearSetService gearSetDao) {
        this.staticDao = staticDao;
        this.gearDao = gearDao;
        this.gearSetDao = gearSetDao;
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

            List<Gear> gearMatches = new ArrayList<>();

            // Get Gear Matches from Loadstone
            for (LSItem item : character.getGearSet()) {
                gearMatches.add(gearDao.getGears().findGearByName(item.getName()));
            }

            // Adds weapon
            gearMatches.add(gearDao.getGears().findGearByName(character.getWeapon().getName()));

            GearSet gearSet = new GearSet(member.getAssignedJob(), member.getPlayer(), gearMatches);

            gearSetDao.getGearSets().save(gearSet);
            staticDao.getStatics().updateMemberGearSet(memberId, gearSet.getId());
        } catch (NullPointerException e) {
            // Cant find member
            if (memberId == null) return new ResponseError("display", "Member not found");
            return new ResponseError("display","unable to determine error");
        }
      /*  // Send Char to view Test!
        model.addAttribute("character",character);
        model.addAttribute("player",member.getPlayer());
        model.addAttribute("gearMatches", gearMatches);*/

      return new Response(true);
    }

}
