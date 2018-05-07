package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.services.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
    StaticService statics;

    @Autowired
    public StaticController(StaticService statics) {
        this.statics = statics;
    }

    // Create a Static
    @GetMapping("/static/create")
    public String createStatic() {
        // Require being logged in
        // Require to not already have a static
        return "static/create";
    }

    // View Static
    @GetMapping("/static/view")
    public String viewStatic(){
        return "static/view";
    }

}
