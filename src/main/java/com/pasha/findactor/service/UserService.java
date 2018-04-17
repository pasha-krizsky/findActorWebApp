package com.pasha.findactor.service;

import com.pasha.findactor.model.User;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public interface UserService {

    User findById(int id);

    User findBySSO(String sso);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserBySSO(String sso);

    List<User> findAllUsers();

    boolean isUserSSOUnique(Integer id, String sso);
}
