package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.Player;
import com.xiv.gearplanner.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PlayerController {
    PlayerService playerDao;

    @Autowired
    public PlayerController(PlayerService playerDao) {
        this.playerDao = playerDao;
    }

    @GetMapping("/player")
    private String viewPlayers(Model model) {
        model.addAttribute("players", playerDao.getPlayers().findAll());
        return "player/index";
    }

    @GetMapping("/player/add")
    private String addPlayer(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("jobs", playerDao.getJobs().findAll());
        return "player/add";
    }

    @PostMapping("/player/add")
    private String addPlayerSubmit(@RequestParam("job") String jobId, @Valid Player player, Errors validation, Model model) {

        if(validation.hasErrors()) {
            model.addAttribute("player", player);
            model.addAttribute("jobs", playerDao.getJobs().findAll());
            model.addAttribute("errors", validation);
            return "player/add";
        }

        player.addJob(playerDao.getJobs().findById(Long.parseLong(jobId)).get());

        playerDao.save(player);

        return "redirect:/player";
    }

}
