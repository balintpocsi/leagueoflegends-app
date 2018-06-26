package com.reaktorlabs.repository;

import com.reaktorlabs.model.User;
import com.reaktorlabs.utility.HashUtils;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Viki
 */
@Stateless
public class UserRepository implements UserRepositoryInterface {
    @PersistenceContext(name = "helloLOL")
    private EntityManager entityManager;

    @Override
    public void create(User entity) {
        entityManager.persist(entity);    
    }

    @Override
    public User read(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> readAll() {
        final TypedQuery<User> findAllUsers 
                = entityManager.createQuery("SELECT u FROM User u", User.class);
        return findAllUsers.getResultList();
    }

    @Override
    public User update(User entity) {
        String password = entity.getPassword();
        final String encryptedPassword = HashUtils.encryptSHA512(password);
        entity.setPassword(encryptedPassword);
        return entityManager.merge(entity);
    }

    @Override
    public User delete(User entity) {
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public User deleteById(Long id) {
        final User user = read(id);
        if(null != user) {
            entityManager.remove(user);
            return user;
        }
        throw new RuntimeException("Can not find user with id: " + id);
    }

    @Override
    public User findByEmail(String email) {
        final TypedQuery<User> findUserByEmail = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        findUserByEmail.setParameter("email", email);
        List<User> myUsers = findUserByEmail.getResultList();
        
        if(myUsers.size() == 1) {
            return myUsers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Long getUserIdByEmail(String email) {
        final TypedQuery<Long> userId = entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :email", Long.class);
        userId.setParameter("email", email);
        return userId.getSingleResult();
    }

    @Override
    public Map<Long, String> getUsersForNotify() {
        final Query users = entityManager.createNativeQuery("select a.app_user_id, a.email from (select app_user.email, blood_donation.app_user_id, max(donation_date) from blood_donation inner join app_user on app_user.id = blood_donation.app_user_id group by app_user_id, email having max(blood_donation.donation_date) <= current_timestamp - interval '56 days') as a left join (select app_user_id, max(sent_time) as maxsenttime from sent_email group by app_user_id) as b  on a.app_user_id = b.app_user_id where maxsenttime <= current_timestamp - interval '56 days' or maxsenttime is null");
        List<Object[]> resultList = users.getResultList();
        Map<Long, String> resultMap = new HashMap<>();

        for (int i = 0; i < resultList.size(); i++) {
            resultMap.put(((BigInteger)resultList.get(i)[0]).longValue(), (String)resultList.get(i)[1]);
        }
        
        return resultMap;
    }

    @Override
    public List<String> sendAlertToWho() {
        final Query users = entityManager.createNativeQuery("select a.email from (select app_user.email, blood_donation.app_user_id, max(donation_date) from blood_donation inner join app_user on app_user.id = blood_donation.app_user_id group by app_user_id, email having max(blood_donation.donation_date) <= current_timestamp - interval '56 days') as a");
        List<String> resultList = users.getResultList();

        return resultList;
    }

}
