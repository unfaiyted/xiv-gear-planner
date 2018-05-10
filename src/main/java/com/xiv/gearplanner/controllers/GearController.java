package com.xiv.gearplanner.controllers;


import com.xiv.gearplanner.models.Gear;
import com.xiv.gearplanner.models.GearStat;
import com.xiv.gearplanner.models.GearStatType;
import com.xiv.gearplanner.models.GearType;
import com.xiv.gearplanner.services.GearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GearController {
   private GearService gear;

    @Autowired
    public GearController(GearService gear) {
        this.gear = gear;
    }


    @GetMapping("/gear")
    public String addMain(Model model) {
        model.addAttribute("gear", new Gear());
        model.addAttribute("stat", new GearStat());
        model.addAttribute("statTypes", gear.getGears().getStatTypes());
        model.addAttribute("types", gear.getGears().getGearTypes());

        return "gear/index";
    }

    @PostMapping("/gear")
    public String addGearSubmit(@RequestParam("stat-type") String type,
                                @RequestParam("value") String value,
                                @Valid Gear newGear, Model model) {

        newGear.addGearStat(gear.convertStatList(type,value));

        gear.save(newGear);

        return "redirect:/gear";
    }

    @GetMapping("/gear/view")
    public String viewGear(Model model) {
        model.addAttribute("gear", gear.getGears().findAll());
        return "gear/view";
    }

    @GetMapping("/gear/add")
    public String addGear(Model model) {
        model.addAttribute("gear", new Gear());
        return  "gear/add";
    }

    @GetMapping("/gear/add/stat-type")
    public String addStatType(Model model) {
        model.addAttribute("statTypes",new GearStatType());
        return "gear/addStatType";
    }

    @GetMapping("/gear/add/type")
    public String addType(Model model) {
        model.addAttribute("type",new GearType());
        return "gear/addType";
    }

    @PostMapping("/gear/add/type")
    public String addTypeSubmit(@Valid GearType type, Model model) {

        return "redirect:/gear";
    }



}
