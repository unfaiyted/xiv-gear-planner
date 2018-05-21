package com.xiv.gearplanner.controllers.util;

import com.xiv.gearplanner.models.ExtendedSocialUser;
import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    private UserService userDao;


    @Autowired
    public UserUtil(UserService userDao) {
        this.userDao = userDao;
    }


    // Checks how is authenticated and then passes the user from that
    // checks type of authentication first.
    // null if not auth
    public User getLoggedInUser() {

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            try {
              return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } catch (ClassCastException e) {
               ExtendedSocialUser socialUser = (ExtendedSocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              return userDao.getUsers().findByUsername(socialUser.getUserId());
            }
        }
        return null;

    }








}
