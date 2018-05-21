package com.xiv.gearplanner.controllers;

import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.models.UserProfile;
import com.xiv.gearplanner.models.UserRole;
import com.xiv.gearplanner.models.UserWithRoles;
import com.xiv.gearplanner.repositories.Roles;
import com.xiv.gearplanner.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private Roles roles;


    @Autowired
    public UserController(Users users, PasswordEncoder passwordEncoder, Roles roles) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.roles = roles;

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, @ModelAttribute UserProfile profile) {

        System.out.println(profile.toString());

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setCreatedAt(LocalDateTime.now());

        System.out.println(user.toString());
        users.save(user);
        users.addDefaultRole(user.getId());

//        users.addProfile(user.getId(),
//                profile.getEmail(),
//                profile.getFirstName(),
//                profile.getLastName(),
//                profile.getName(),
//                profile.getUsername());




        authenticate(user);
        return "redirect:/login";
    }
    

    @GetMapping("/profile")
    public String loadProfile(Model model) {
        return "users/profile";
    }


    private void authenticate(User user) {
        UserDetails userDetails = new UserWithRoles(user, roles.ofUserWith(user.getUsername()));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }

}
