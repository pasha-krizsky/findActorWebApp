package com.pasha.findactor.converter;

import com.pasha.findactor.model.UserProfile;
import com.pasha.findactor.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile> {

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Creates {@link UserProfile} object by id.
     *
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public UserProfile convert(Object element) {
        Integer id = Integer.parseInt((String) element);
        UserProfile profile = userProfileService.findById(id);
        log.info("Profile : {}", profile);
        return profile;
    }
}
