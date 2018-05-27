package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.services.StaticService;
import com.xiv.gearplanner.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StaticController {
    private StaticService staticDao;
    private UserService userDao;

    public StaticController(StaticService staticDao, UserService userDao) {
        this.staticDao = staticDao;
        this.userDao = userDao;
    }

    // Create a Static
    @GetMapping("/static/create")
    public String createStatic() {
        // Require being logged in
        // Require to not already have a static
        return "static/create";
    }

    @PostMapping("/static/create")
    public String action(@RequestParam(value = "member[]") Long[] playerIds,
                       @RequestParam(value = "static-name") String name){

        User loggedIn = userDao.getLoggedInUser();
        Static newStatic = new Static(name, loggedIn);
        // add members
        newStatic.addMembers(staticDao.createStaticMembersbyPlayerId(playerIds));
        staticDao.save(newStatic);

        return "redirect:/static/view";
    }

    @GetMapping("/static/delete")
    public String deleteStatic(Model model) {


        Static userStatic = staticDao.getStatics().getStaticByOwner(userDao.getLoggedInUser().getId());

        staticDao.getStatics().delete(userStatic);

        return "static/delete";
    }

    // View Static
    @GetMapping("/static/view")
    public String viewStatic(Model model){

        if (staticDao.isOwner(userDao.getLoggedInUser().getId())) {
            List<StaticMember> staticMembers = staticDao.getStatics().getMembersByOwnerId(userDao.getLoggedInUser().getId());
            Static myStatic = staticDao.getStatics().getStaticByOwner(userDao.getLoggedInUser().getId());

            model.addAttribute("static", myStatic);
            model.addAttribute("members", staticMembers);
            return "static/view";
        }
        return("redirect:/static/create");
    }


    @GetMapping("/static/view/{id}")
    public String viewStaticMember(@PathVariable Long id, Model model) {

        StaticMember member =  staticDao.getStatics().getMember(id);
        model.addAttribute("member", member);
        return "static/viewMember";

    }



}
