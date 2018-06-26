package com.reaktorlabs.service;

import com.reaktorlabs.model.User;

/**
 *
 * @author Viki
 */
public interface AuthenticationInterface {
    String login(User user);
    void logout();
    void register(User user);
}
