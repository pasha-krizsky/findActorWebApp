package com.pasha.findactor.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public abstract class AbstractController {

    /**
     * Returns the principal[user-name] of logged-in user.
     */
    protected String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
