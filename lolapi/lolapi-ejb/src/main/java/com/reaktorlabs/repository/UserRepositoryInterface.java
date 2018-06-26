package com.reaktorlabs.repository;

import com.reaktorlabs.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Viki
 */
public interface UserRepositoryInterface extends GenericRepository<Long, User>{
    User findByEmail(String email);
    Long getUserIdByEmail(String email);   
    Map<Long, String> getUsersForNotify();
    List<String> sendAlertToWho();
}