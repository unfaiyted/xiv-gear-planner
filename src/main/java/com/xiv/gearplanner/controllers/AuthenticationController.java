package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User();
        model.addAttribute("user",user);

        Authentication token = SecurityContextHolder.getContext().getAuthentication();

        // not logged in
        if (token instanceof AnonymousAuthenticationToken) {
            return "users/login";
        }

        // redirect to home page
        return String.format("redirect:%s", "/");

    }


}
