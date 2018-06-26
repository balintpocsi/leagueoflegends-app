package com.reaktorlabs.service;

import com.reaktorlabs.model.User;
import com.reaktorlabs.repository.UserRepositoryInterface;
import com.reaktorlabs.utility.HashUtils;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Viki
 */
public class AuthenticationService implements AuthenticationInterface {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    
    @Inject
    private UserRepositoryInterface repository;
    
    @Inject
    private HttpServletRequest servletRequest;

    @Override
    public String login(User user) {
        try {
            HttpSession mySession = servletRequest.getSession();
            servletRequest.login(user.getEmail(), user.getPassword());
            mySession.setAttribute("user_email", user.getEmail());
            return "ok";
        } catch (ServletException ex) {
            LOGGER.warning(ex.getMessage());
            return "no";
        }
    }

    @Override
    public void logout() {
        try {
            servletRequest.getSession();
            servletRequest.logout();
        } catch (ServletException ex) {
            LOGGER.warning(ex.getMessage());
        }
    }

    @Override
    public void register(User user) {
        final User newUser = new User();
        final String password = user.getPassword();
        final String encryptedPassword = HashUtils.encryptSHA512(password);
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setName(user.getName());
        
        repository.create(newUser);
    }
    
}
