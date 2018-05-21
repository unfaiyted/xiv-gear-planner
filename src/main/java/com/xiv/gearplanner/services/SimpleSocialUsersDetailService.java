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


public class SimpleSocialUsersDetailService implements SocialUserDetailsService {

    private UserDetailsLoader userDetailsService;
  //  private Users users;

    public SimpleSocialUsersDetailService(UserDetailsLoader userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UserProfile profile = userDetailsService.getProfile(userId);

        //return new ExtendedSocialUser(userDetails.getUsername(), userDetails.getPassword(),
          //      userDetails.getAuthorities(), userDetailsService.);
        return new ExtendedSocialUser(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities(), profile);
    }

}