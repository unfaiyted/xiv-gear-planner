package com.xiv.gearplanner;

import com.xiv.gearplanner.services.AccountConnectionSignUpService;
import com.xiv.gearplanner.services.UserService;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;


@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {
    private UserService usersDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public SocialConfiguration(UserService usersDao ) {
        this.usersDao = usersDao;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

            connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("spring.social.facebook.appId"),
                environment.getProperty("spring.social.facebook.appSecret")));
    //        connectionFactoryConfigurer.addConnectionFactory(new TwitterConnectionFactory(
    //            environment.getProperty("twitter.consumerKey"),
    //            environment.getProperty("twitter.consumerSecret")));

        connectionFactoryConfigurer.addConnectionFactory(new GoogleConnectionFactory(
            environment.getProperty("spring.social.google.appId"),
            environment.getProperty("spring.social.google.appSecret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

   @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

        JdbcUsersConnectionRepository repository = new
                JdbcUsersConnectionRepository(dataSource,
                        connectionFactoryLocator, Encryptors.noOpText());

        repository.setConnectionSignUp(new AccountConnectionSignUpService(usersDao));

        return repository;
    }
}
