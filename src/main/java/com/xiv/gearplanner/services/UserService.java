package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.models.UserProfile;
import com.xiv.gearplanner.repositories.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class UserService {
    private Users users;

    @Autowired
    public UserService(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    public void createUser(String username, UserProfile profile) {
        users.addUser(username, RandomStringUtils.randomAlphanumeric(8));
        // get user ID
        User user = users.findByUsername(username);
        users.addDefaultRole(user.getId());
        users.addProfile(user.getId(),
                profile.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getName(),
                profile.getUsername());

    }
}
