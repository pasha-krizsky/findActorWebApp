package com.pasha.findactor.service;

import com.pasha.findactor.dao.UserDao;
import com.pasha.findactor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(int id) {
        return dao.findById(id);
    }

    @Override
    public User findBySSO(String sso) {
        return dao.findBySso(sso);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    @Override
    public void updateUser(User user) {
        User userFromDatabase = dao.findById(user.getId());
        if (userFromDatabase != null) {
            userFromDatabase.setSsoId(user.getSsoId());
            if (!user.getPassword().equals(userFromDatabase.getPassword())) {
                userFromDatabase.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userFromDatabase.setFirstName(user.getFirstName());
            userFromDatabase.setLastName(user.getLastName());
            userFromDatabase.setEmail(user.getEmail());
            userFromDatabase.setUserProfiles(user.getUserProfiles());
        }
    }

    @Override
    public void deleteUserBySSO(String sso) {
        dao.deleteBySso(sso);
    }

    @Override
    public List<User> findAllUsers() {
        return dao.findAll();
    }

    @Override
    public boolean isUserSSOUnique(Integer id, String sso) {
        User user = findBySSO(sso);
        return (user == null || ((id != null) && (user.getId().equals(id))));
    }
}
