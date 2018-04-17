package com.pasha.findactor.dao;

import com.pasha.findactor.model.UserProfile;

import java.util.List;

/**
 * This interface defines a contract for database interactions
 * related to {@link com.pasha.findactor.model.constants.Tables#USER_PROFILE} table.
 *
 * @author Pavel.Krizskiy
 * @see UserProfile
 * @since 1.0.0
 */
public interface UserProfileDao {

    /**
     * Returns all {@link UserProfile} objects from database.
     */
    List<UserProfile> findAll();

    /**
     * Returns {@link UserProfile} object from database by its type.
     */
    UserProfile findByType(String type);

    /**
     * Returns {@link UserProfile} object from database by its id.
     */
    UserProfile findById(int id);
}
