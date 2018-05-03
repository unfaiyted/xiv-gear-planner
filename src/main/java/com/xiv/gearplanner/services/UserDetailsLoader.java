package com.xiv.gearplanner.services;


import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.models.UserWithRoles;
import com.xiv.gearplanner.repositories.Roles;
import com.xiv.gearplanner.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
public class UserDetailsLoader implements UserDetailsService {
    private final Users users;
    private final Roles roles;

    @Autowired
    public UserDetailsLoader(Users users, Roles roles) {
                this.users = users;
                this.roles = roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = users.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User could not be found for " + username);
        }

        return new UserWithRoles(user, roles.ofUserWith(username));

    }


}
