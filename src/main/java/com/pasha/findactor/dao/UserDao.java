package com.pasha.findactor.dao;

import com.pasha.findactor.model.User;

import java.util.List;

/**
 * This interface defines a contract for database interactions
 * related to {@link com.pasha.findactor.model.constants.Tables#APP_USER} table.
 *
 * @author Pavel.Krizskiy
 * @see User
 * @see UserDaoImpl
 * @since 1.0.0
 */
public interface UserDao {

    /**
     * Finds {@link User} object in database by its id.
     *
     * @param id user id
     * @return {@link User} object
     */
    User findById(int id);

    /**
     * Finds {@link User} object in database by its sso.
     *
     * @param sso sso of the user
     * @return {@link User} object
     */
    User findBySso(String sso);

    /**
     * Saves {@link User} object in database.
     *
     * @param user {@link User} object to save in database
     */
    void save(User user);

    /**
     * Updates {@link User} object in database.
     *
     * @param user {@link User} object to update in database
     */
    void update(User user);

    /**
     * Deletes {@link User} object from database using its sso (nickname).
     *
     * @param sso sso of the user
     */
    void deleteBySso(String sso);

    /**
     * Finds and returns all registered users.
     *
     * @return list of registered users
     */
    List<User> findAll();
}
