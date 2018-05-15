package com.xiv.gearplanner.controllers.Importers;


import com.xiv.gearplanner.exceptions.LodestoneParserException;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.parser.ParserUtils;
import com.xiv.gearplanner.services.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class LodestoneImportController {
    private static PlayerService playerDao;
    private static GearService gearDao;
    private static JobService jobDao;
    private static GearSetService gearSetDao;
    private static StaticService staticDao;

    private static final String ALL_CLASSES = "All Classes";

    @Autowired
    public LodestoneImportController(PlayerService playerDao, GearService gearDao, JobService jobDao, GearSetService gearSetDao) {
        this.playerDao = playerDao;
        this.gearDao = gearDao;
        this.jobDao = jobDao;
        this.gearSetDao = gearSetDao;
    }

    @GetMapping("/ls/char")
    public String getCharacter(Model model) throws IOException, LodestoneParserException {

        Long LodeStoneId = 13143212L;

        String charUrl = "https://na.finalfantasyxiv.com/lodestone/character/13143212/";

        Document html = Jsoup.connect(charUrl).get();
       // html.select("p").forEach(System.out::println);
        LSCharacter character = new LSCharacter();
        // Get name, world, title
        parsePlate(character, html);
        // Get gear and level
        parseGearAndLevel(character, html);

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







    /**
     * Parse name, world, and title
     */
    private void parsePlate(LSCharacter character, Document html) throws LodestoneParserException {
        Elements playerNameBox = html.select("div.frame__chara__box");
        ParserUtils.checkElementsSize(playerNameBox, 1, "Cannot find html for character name");

        // get character name
//		Elements pName = playerNameBox.select("p.frame__chara__name");
//		ParserUtils.checkElementsSize(pName, 1, "Cannot find html for character name");

        Elements playerNameTag = playerNameBox.select("p.frame__chara__name");
        ParserUtils.checkElementsSize(playerNameTag, 1, "Cannot find html for character name");
        character.setName(playerNameTag.text());

        // get character world
        Elements playerWorldTag = playerNameBox.select("p.frame__chara__world");
        ParserUtils.checkElementsSize(playerWorldTag, 1, "Cannot find HTML for character world");
        character.setWorld(playerWorldTag.text().replace("(", "").replace(")", "")); // remove parenthesis: "(Ragnarok)" -> "Ragnarok"

        // get character title
        Elements playerTitleTag = playerNameBox.select("p.frame__chara__title");
        if (playerTitleTag.size() > 0) { // a character can have no title
            ParserUtils.checkElementsSize(playerTitleTag, 1, "Cannot find HTML for character title");
            character.setTitle(playerTitleTag.text());
        }
    }

    private void parseClasses(LSCharacter character, Document html) throws LodestoneParserException {
        // TODO
    }

    /**
     * Parse level, weapon, left and right side gear
     * @param character
     * @param html
     * @throws LodestoneParserException
     */
    private void parseGearAndLevel(LSCharacter character, Document html) throws LodestoneParserException {

        // Select the character__profile div of the "Profile" tab (there is on per tab)
        // can't directly select a parent with css selectors, we have to get the character__profile__data__detail which is unique to the profile tab,
        // and then get the n+2 parent
        Elements profileDataDetail = html.select(
                "div.character__content > div.character__profile > div.character__profile__data > div.character__profile__data__detail");
        ParserUtils.checkElementsSize(profileDataDetail, 1, "Cannot find HTML for character profile data detail");

        Element characterProfile = profileDataDetail.first().parent().parent();
        ParserUtils.checkElementClass(characterProfile, "character__profile", "Cannot find HTML for character profile");

        // box with class info and weapon
        Elements classInfo = characterProfile.select("div.character__class");
        ParserUtils.checkElementsSize(classInfo, 1, "Cannot find HTML for character current class info");

        // get level
        Elements level = classInfo.select("div.character__class__data > p");
        ParserUtils.checkElementsSize(level, 1, "Cannot find HTML for character level");
        character.setLevel(ParserUtils.extractNumeric(level.first().text()));

        // get weapon
        Elements weapon = classInfo.select("div.character__class__arms div.item_detail_box");
        ParserUtils.checkElementsSize(weapon, 1, "Cannot find HTML for character weapon");
        LSItem weaponItem = parseItem(weapon.first());
        character.setWeapon(weaponItem);

        // we don't have to parse the current class/job, it can be inferred from weapon + equipped job stone

        // get left and right side gear
//		Elements itemColumns = html.select("div.contents:not(.none) > div.clearfix > div.param_right_area > div#chara_img_area.clearfix > div.icon_area");
        Elements itemColumns = characterProfile.select("div.character__detail > div.character__detail__icon");
        ParserUtils.checkElementsSize(itemColumns, 2, "Cannot find HTML for character left and right side gear");
        List<LSItem> gearSet = new ArrayList<LSItem>();
        for (Element column : itemColumns) { // loop on 2 colums : left and right side
            // items will contain at max 6 items for left side,
            // and at max 7 items for right side (1 shield, 5 accessories, 1 job stone).
            // This selector will NOT retrieve empty slots
            Elements items = column.select("div.item_detail_box");
            for (Element item : items) {
                LSItem gear = parseItem(item);
                gearSet.add(gear);
            }
        }
        character.setGearSet(gearSet);
    }

    private LSItem parseItem(Element element) throws LodestoneParserException {
        LSItem item = new LSItem();

        // get item lodestone id
        Elements lodestoneLink = element.select("div.db-tooltip__bt_item_detail > a");
        ParserUtils.checkElementsSize(lodestoneLink, 1, "Cannot find HTML for lodestone link to character item");
        // format should be "/lodestone/playguide/db/item/<id>/"
        String lodestoneId = lodestoneLink.attr("href").split("/")[5];
        item.setId(lodestoneId);

        // get item name
        Elements itemName = element.select("h2.db-tooltip__item__name");
        ParserUtils.checkElementsSize(itemName, 1, "Cannot find HTML for character item name");
        item.setName(itemName.first().text());

        // get item level
        Elements itemLevel = element.select("div.db-tooltip__item__level");
        ParserUtils.checkElementsSize(itemLevel, 1, "Cannot find HTML for character item level");
        item.setLevel(ParserUtils.extractNumeric(itemLevel.first().text()));

        // get item category
        Elements itemCategory = element.select("p.db-tooltip__item__category");
        ParserUtils.checkElementsSize(itemCategory, 1, "Cannot find HTML for character item category");
        item.setCategory(itemCategory.first().text());

        // get item classes restriction
        Elements itemClasses = element.select("div.db-tooltip__item_equipment__class");
        ParserUtils.checkElementsSize(itemClasses, 1, "Cannot find HTML for character item classes");
        String classes = itemClasses.first().text();
        if (ALL_CLASSES.equals(classes)) {
            item.setClasses(Arrays.asList(new String[]{"All"}));
        } else {
            item.setClasses(Arrays.asList(classes.split(" ")));
        }

        return item;
    }



}
