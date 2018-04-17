package com.pasha.findactor.service;

import com.pasha.findactor.model.UserProfile;

import java.util.List;

/**
 * This interface defines some methods to process retrieved from database data.
 *
 * @author Pavel.Krizskiy
 * @see UserProfile
 * @see com.pasha.findactor.dao.UserProfileDao
 * @since 1.0.0
 */
public interface UserProfileService {

    UserProfile findById(int id);

    UserProfile findByType(String type);

    List<UserProfile> findAll();
}
