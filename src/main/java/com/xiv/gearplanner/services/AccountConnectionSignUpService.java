package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.UUID;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private static final Logger LOG = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    private final UserService usersDao;

    public AccountConnectionSignUpService(UserService usersDao) {
        this.usersDao = usersDao;
    }

    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        String username = UUID.randomUUID().toString();
        LOG.debug("Created username: " + username);
        usersDao.createUser(username, new UserProfile(profile));
        return username;
    }
}