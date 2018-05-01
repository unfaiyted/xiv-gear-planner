package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User();

        //Authentication token = SecurityContextHolder.getContext().getAuthentication();

        // not logged in
        model.addAttribute("user",user);
        return "/users/login";
        // if (token instanceof AnonymousAuthenticationToken)


    }


}
