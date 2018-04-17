package com.pasha.findactor.model.constants;

import com.pasha.findactor.model.UserProfile;
import lombok.Getter;

import java.io.Serializable;

/**
 * This enumeration contains all possible roles,
 * used as type in {@link UserProfile} model.
 *
 * @author Pavel.Krizskiy
 * @see UserProfile
 * @since 1.0.0
 */
public enum UserProfileType implements Serializable {

    /**
     * User with this role can:
     * 1. Submit worksheets.
     * 2. Track status of his submission.
     * 3. Edit submitted worksheet.
     */
    USER("USER"),

    /**
     * User with this role can:
     * 1. View all submitted worksheets.
     * 2. Accept or decline concrete worksheet based on its quality.
     */
    AGENT("AGENT"),

    /**
     * User with this role can:
     * 1. View all accepted by AGENT worksheets.
     * 2. Make offer to user after casting or decline worksheet.
     */
    DIRECTOR("DIRECTOR"),

    /**
     * User with this role can:
     * 1. View all users.
     * 2. Edit all users.
     * 3. Create new users with any roles.
     */
    ADMIN("ADMIN");

    @Getter
    private String userProfileType;

    UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }
}
