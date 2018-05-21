package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.ExtendedSocialUser;
import com.xiv.gearplanner.models.UserProfile;
import com.xiv.gearplanner.repositories.Users;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SimpleSocialUsersDetailService implements SocialUserDetailsService {

    private UserDetailsService userDetailsService;
    private Users users;

    public SimpleSocialUsersDetailService(UserDetailsService userDetailsService, Users users) {
        this.userDetailsService = userDetailsService;
        this.users = users;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UserProfile profile = users.getUserProfileByUsername(userId);
        return new ExtendedSocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities(), profile);
    }

}