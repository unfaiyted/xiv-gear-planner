package com.xiv.gearplanner.controllers;


import com.xiv.gearplanner.models.Player;
import com.xiv.gearplanner.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlayerImportController {
    PlayerService players;

    @Autowired
    public PlayerImportController(PlayerService players) {
            this.players = players;
    }
    //Player search
    @GetMapping("/import/player")
    public String playerSearch() {
        return "import/player";
    }

    // Player search return
    @PostMapping("/import/player")
    public String playerReturn() {
        return "import/player";
    }

    //Player Import
    @PostMapping("/import/player/{id}")
    public String playerSelected() {
        return "import/player/{id}";
    }


    @RequestMapping(
            value = "/api/player/add",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Player dataImportPlayer (@RequestBody Player player) {
        players.save(player);
        return player;
    }


}
