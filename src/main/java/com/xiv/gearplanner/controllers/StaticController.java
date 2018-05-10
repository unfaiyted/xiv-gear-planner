package com.xiv.gearplanner.controllers;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.services.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StaticController {
    StaticService staticDao;

    @Autowired
    public StaticController(StaticService staticDao) {
        this.staticDao = staticDao;
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

        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Static newStatic = new Static(name, loggedIn);
        // add members
        newStatic.addMembers(staticDao.createStaticMembersbyPlayerId(playerIds));
        staticDao.save(newStatic);

        return "redirect:/static/view";
    }

    @GetMapping("/static/delete")
    public String deleteStatic(Model model) {

        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Static userStatic = staticDao.getStatics().getStaticByOwner(loggedIn.getId());

        staticDao.getStatics().delete(userStatic);

        return "static/delete";
    }

    // View Static
    @GetMapping("/static/view")
    public String viewStatic(Model model){

        User loggedin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<StaticMember> staticMembers =  staticDao.getStatics().getMembersByOwnerId(loggedin.getId());
        Static myStatic = staticDao.getStatics().getStaticByOwner(loggedin.getId());

        model.addAttribute("static", myStatic);
        model.addAttribute("members", staticMembers);
        return "static/view";
    }


    @GetMapping("/static/view/{id}")
    public String viewStaticMember(@PathVariable Long id, Model model) {

        StaticMember member =  staticDao.getStatics().getMember(id);

        model.addAttribute("member", member);
        return "static/viewMember";

    }


    @RequestMapping(
            value = "/api/static/job/update",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public StaticMember editMemberAssignedJob (@RequestBody String jsonStr) throws IOException {

       jsonStr = jsonStr.replaceAll("^\"|\"$|\\\\", "");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonStr);

        JsonNode idNode = actualObj.path("memberId");
        JsonNode jobIdNode = actualObj.path("jobId");

        Long memberId = idNode.asLong();
        Long jobId = jobIdNode.asLong();

        // Updates member job
        staticDao.getStatics().updateMemberJob(memberId, jobId);

        return  staticDao.getStatics().getMember(memberId);
    }


    @RequestMapping(
            value = "/api/static/member/delete",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response deleteMember (@RequestBody String jsonStr) throws IOException {
        try {

            jsonStr = jsonStr.replaceAll("^\"|\"$|\\\\", "");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(jsonStr);

            JsonNode idNode = actualObj.path("memberId");

            Long memberId = idNode.asLong();
            staticDao.getStatics().deleteMember(memberId);

        } catch(IOException err) {

            ResponseError error = new ResponseError();
            // fill map with errors here
            return error;
        }

        Response res = new Response();
        res.setSuccess(true);
        return res;
    }

}
