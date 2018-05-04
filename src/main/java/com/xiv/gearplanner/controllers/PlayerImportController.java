package com.xiv.gearplanner.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerImportController {


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

}
