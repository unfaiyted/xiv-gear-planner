package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.controllers.util.SocialControllerUtil;
import com.xiv.gearplanner.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class AuthenticationController {
    private SocialControllerUtil util;

    @Autowired
    public AuthenticationController(SocialControllerUtil util) {
        this.util = util;
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Principal currentUser, Model model) {
        User user = new User();

        util.setModel(request, currentUser, model);

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
