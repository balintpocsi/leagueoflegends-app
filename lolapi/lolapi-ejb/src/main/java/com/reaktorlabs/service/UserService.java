package com.reaktorlabs.service;

import com.reaktorlabs.model.User;
import com.reaktorlabs.repository.UserRepositoryInterface;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Viki
 */
@Stateless
public class UserService implements UserDataInterface {
    @Inject
    private UserRepositoryInterface repository;

    @Override
    public User getUserById(Long id) {
        return repository.read(id);
    }

    @Override
    public User updateUser(User user) {
        return repository.update(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return repository.getUserIdByEmail(email);
    }

    @Override
    public Map<Long, String> getSendEmailToWho() {
        return repository.getUsersForNotify();
    }

    @Override
    public List<String> getSendAlertToWho() {
        return repository.sendAlertToWho();
    }
}
