package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayerController {
    PlayerService playerDao;

    @GetMapping("/players")
    private String viewPlayers(Model model) {
        model.addAttribute("players", playerDao.getPlayers().findAll());
        return "/player/index";
    }



}
