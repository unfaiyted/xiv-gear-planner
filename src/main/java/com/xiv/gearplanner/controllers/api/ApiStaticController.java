package com.xiv.gearplanner.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.Response;
import com.xiv.gearplanner.models.ResponseError;
import com.xiv.gearplanner.models.StaticMember;
import com.xiv.gearplanner.services.StaticService;
import com.xiv.gearplanner.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/static")
public class ApiStaticController {
    private StaticService staticDao;
    private UserService userDao;

    public ApiStaticController(StaticService staticDao, UserService userDao) {
        this.staticDao = staticDao;
        this.userDao = userDao;


    }

    @RequestMapping(
            value = "/job/update",
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
            value = "/member/delete",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response deleteMember (@RequestBody String jsonStr) throws IOException {
        try {
            JsonNode actualObj = strToJsonNode(jsonStr);
            JsonNode idNode = actualObj.path("memberId");
            Long memberId = idNode.asLong();

            staticDao.getStatics().deleteMember(memberId);

        } catch(IOException err) {
            // fill map with errors here
            return new ResponseError();
        }

        Response res = new Response();
        res.setSuccess(true);
        return res;
    }


    @RequestMapping(
            value = "/member/add",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response addMember (@RequestBody String jsonStr) throws IOException {
        try {
            JsonNode actualObj = strToJsonNode(jsonStr);
            Long staticId = staticDao.getStatics().getStaticIdByOwner(userDao.getLoggedInUser().getId());

            for(JsonNode member : actualObj) {
                Long playerId =  member.path("id").asLong();
                staticDao.getStatics().addMember(staticId, playerId);
            }

        } catch(IOException err) {
            // fill map with errors here
            return new ResponseError();
        }

        Response res = new Response();
        res.setSuccess(true);
        return res;
    }


    // package json node mapping from string
    private JsonNode strToJsonNode(String jsonStr)  throws IOException {
        jsonStr = jsonStr.replaceAll("^\"|\"$|\\\\", "");
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(jsonStr);
    }
}
