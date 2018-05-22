package com.xiv.gearplanner.controllers;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.controllers.util.UserUtil;
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
    UserUtil userUtil;


    @Autowired
    public StaticController(StaticService staticDao, UserUtil userUtil) {
        this.staticDao = staticDao;
        this.userUtil = userUtil;


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

        User loggedIn = userUtil.getLoggedInUser();
        Static newStatic = new Static(name, loggedIn);
        // add members
        newStatic.addMembers(staticDao.createStaticMembersbyPlayerId(playerIds));
        staticDao.save(newStatic);

        return "redirect:/static/view";
    }

    @GetMapping("/static/delete")
    public String deleteStatic(Model model) {


        Static userStatic = staticDao.getStatics().getStaticByOwner(userUtil.getLoggedInUser().getId());

        staticDao.getStatics().delete(userStatic);

        return "static/delete";
    }

    // View Static
    @GetMapping("/static/view")
    public String viewStatic(Model model){

        if (staticDao.isOwner(userUtil.getLoggedInUser().getId())) {
            List<StaticMember> staticMembers = staticDao.getStatics().getMembersByOwnerId(userUtil.getLoggedInUser().getId());
            Static myStatic = staticDao.getStatics().getStaticByOwner(userUtil.getLoggedInUser().getId());

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

    @RequestMapping(
            value = "/api/static/job/update",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public StaticMember editMemberAssignedJob (@RequestBody String jsonStr) throws IOException {

        JsonNode actualObj = strToJsonNode(jsonStr);

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
            JsonNode actualObj = strToJsonNode(jsonStr);
            JsonNode idNode = actualObj.path("memberId");
            Long memberId = idNode.asLong();

            System.out.println(memberId);
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


    @RequestMapping(
            value = "/api/static/member/add",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response addMember (@RequestBody String jsonStr) throws IOException {
        try {
           JsonNode actualObj = strToJsonNode(jsonStr);
            Long staticId = staticDao.getStatics().getStaticIdByOwner(userUtil.getLoggedInUser().getId());

            for(JsonNode member : actualObj) {
                Long playerId =  member.path("id").asLong();
                staticDao.getStatics().addMember(staticId, playerId);
            }

        } catch(IOException err) {

            ResponseError error = new ResponseError();
            // fill map with errors here
            return error;
        }

        Response res = new Response();
        res.setSuccess(true);
        return res;
    }



    // package json node mapping from string
    private JsonNode strToJsonNode(String jsonStr)  throws IOException {

        jsonStr = jsonStr.replaceAll("^\"|\"$|\\\\", "");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualNode = mapper.readTree(jsonStr);

        return actualNode;
    }


}
