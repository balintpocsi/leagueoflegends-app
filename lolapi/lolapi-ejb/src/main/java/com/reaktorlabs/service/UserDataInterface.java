package com.reaktorlabs.service;

import com.reaktorlabs.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Viki
 */
public interface UserDataInterface {
    User getUserById(Long id);
    User updateUser(User user);
    User getUserByEmail(String email);
    Long getUserIdByEmail(String email);
    Map<Long, String> getSendEmailToWho();
    List<String> getSendAlertToWho();
}
