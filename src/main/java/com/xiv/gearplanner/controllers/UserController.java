package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.repositories.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class UserController {
    private Users users;
    private PasswordEncoder passwordEncoder;


    public UserController(Users users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setCreatedAt(LocalDateTime.now());
        users.save(user);
        return "redirect:/login";
    }
    

    @GetMapping("/profile")
    public String loadProfile(Model model) {

        return "users/profile";
    }


}
