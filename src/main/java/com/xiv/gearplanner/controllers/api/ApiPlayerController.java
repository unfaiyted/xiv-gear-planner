package com.xiv.gearplanner.controllers.api;

import com.xiv.gearplanner.models.Player;
import com.xiv.gearplanner.services.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/players")
public class ApiPlayerController {
    private PlayerService playerDao;

    public ApiPlayerController(PlayerService playerDao) {
        this.playerDao = playerDao;
    }

    // Return JSON data
    @GetMapping("/{search}")
    private @ResponseBody
    List<Player> playersJson(@PathVariable String search) {
        System.out.println(search);
        return playerDao.getPlayers().findPlayersByNameContaining(search);
    }
}
