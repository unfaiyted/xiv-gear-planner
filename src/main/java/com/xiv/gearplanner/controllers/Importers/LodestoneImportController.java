package com.xiv.gearplanner.controllers.Importers;


import com.xiv.gearplanner.exceptions.LodestoneParserException;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.parser.CharacterParser;
import com.xiv.gearplanner.parser.URLS;
import com.xiv.gearplanner.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LodestoneImportController {
    private static PlayerService playerDao;
    private static GearService gearDao;
    private static JobService jobDao;
    private static GearSetService gearSetDao;
    private static StaticService staticDao;

    @Autowired
    public LodestoneImportController(PlayerService playerDao, GearService gearDao, JobService jobDao, GearSetService gearSetDao) {
        this.playerDao = playerDao;
        this.gearDao = gearDao;
        this.jobDao = jobDao;
        this.gearSetDao = gearSetDao;
    }

    @GetMapping("/lodestone/import")
    public String getCharacter(Model model) throws IOException, LodestoneParserException {

        Long LodeStoneId = 13143212L;

        CharacterParser parser = new CharacterParser(URLS.US);

        LSCharacter character = parser.getCharacterById(LodeStoneId);

        Player player = playerDao.getPlayers().findFirstPlayerByLoadstoneId(LodeStoneId);

        List<Gear> gearMatches = new ArrayList<>();

        // Get Gear Matches from Loadstone
        for(LSItem item : character.getGearSet()) {
            gearMatches.add(gearDao.getGears().findGearByName(item.getName()));
            }

            //TODO: check if count matches number of gear valid for set.
        // add weapon check

        String jobAbbr = character.getClassOrJob(); // current equipped job
        Job job = jobDao.getJobs().findJobByAbbr(jobAbbr);
        GearSet gearSet = new GearSet(job, player,gearMatches);

        Long memberId = 3L;

        StaticMember member = staticDao.getStatics().getMember(memberId);

        gearSetDao.getGearSets().save(gearSet);

        //staticDao.getStatics().updateMemberGearSet(gearSet.getId());

       //

        // Send Char to view Test!
        model.addAttribute("character",character);
        model.addAttribute("player",player);
        model.addAttribute("gearMatches", gearMatches);
        return "/import/lsPlayer";
    }

}
