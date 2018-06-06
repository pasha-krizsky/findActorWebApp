package com.pasha.findactor.controller;

import com.pasha.findactor.common.Urls;
import com.pasha.findactor.common.Views;
import com.pasha.findactor.model.User;
import com.pasha.findactor.model.UserProfile;
import com.pasha.findactor.model.Worksheet;
import com.pasha.findactor.model.constants.UserProfileType;
import com.pasha.findactor.service.UserProfileService;
import com.pasha.findactor.service.UserService;
import com.pasha.findactor.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Controller
@SessionAttributes("roles")
public class UserController extends AbstractController {

    private static final String LOGGED_IN_USER_ATTR = "loggedinuser";
    private static final String USERS_ATTR = "users";
    private static final String USER_ATTR = "user";
    private static final String EDIT_ATTR = "edit";
    private static final String SUCCESS_ATTR = "success";

    private static final String NON_UNIQUE_SSO_ID_MSG = "non.unique.ssoId";

    @Autowired
    private UserService userService;

    @Autowired
    private WorksheetService worksheetService;

    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Provides {@link UserProfile} list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    /**
     * Provides the medium to register a new user.
     */
    @RequestMapping(value = {Urls.REGISTER_USER_URL}, method = RequestMethod.GET)
    public String registerUser(ModelMap model) {
        User user = new User();

        model.addAttribute(USER_ATTR, user);
        model.addAttribute(EDIT_ATTR, false);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.REGISTRATION;
    }

    /**
     * Saves a new user in database. Validates the user input.
     */
    @RequestMapping(value = {Urls.REGISTER_USER_URL}, method = RequestMethod.POST)
    public String registerUser(@Valid User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return Views.REGISTRATION;
        }

        if (CollectionUtils.isEmpty(user.getUserProfiles())) {
            user.getUserProfiles().add(obtainUserProfile());
        }

        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            FieldError ssoError = new FieldError(
                    "user",
                    "ssoId",
                    messageSource.getMessage(NON_UNIQUE_SSO_ID_MSG, new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);

            return Views.REGISTRATION;
        }

        userService.saveUser(user);

        String successMessage = "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully";
        model.addAttribute(SUCCESS_ATTR, successMessage);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.REGISTRATION_SUCCESS;
    }

    /**
     * Lists all registered users.
     */
    @RequestMapping(value = {Urls.LIST_USERS_URL}, method = RequestMethod.GET)
    public String listAllUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();

        model.addAttribute(USERS_ATTR, users);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.USERS_LIST;
    }

    /**
     * Provides the medium to update an existing user.
     */
    @RequestMapping(value = {Urls.EDIT_USER_URL}, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);

        model.addAttribute(USER_ATTR, user);
        model.addAttribute(EDIT_ATTR, true);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.REGISTRATION;
    }

    /**
     * Updates user in database.
     */
    @RequestMapping(value = {Urls.EDIT_USER_URL}, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String ssoId) {
        if (result.hasErrors()) {
            return Views.REGISTRATION;
        }

        userService.updateUser(user);

        String successMessage = "User " + user.getFirstName() + " " + user.getLastName() + " updated successfully";
        model.addAttribute(SUCCESS_ATTR, successMessage);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.REGISTRATION_SUCCESS;
    }

    /**
     * Deletes user from database.
     */
    @RequestMapping(value = {Urls.DELETE_USER_URL}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        User user = userService.findBySSO(ssoId);
        Worksheet worksheet = worksheetService.findByUserId(user.getId());
        if (worksheet != null) {
            worksheetService.deleteById(worksheet.getId());
        } else {
            userService.deleteUserBySSO(ssoId);
        }
        return Views.REDIRECT_LIST;
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = Urls.LOGIN_URL, method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return Views.LOGIN;
        } else {
            return Views.REDIRECT_LIST;
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = Urls.LOGOUT_URL, method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Views.REDIRECT_LOGIN_LOGOUT;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    private UserProfile obtainUserProfile() {
        List<UserProfile> profiles = userProfileService.findAll();
        UserProfile userProfile = null;
        for (UserProfile profile : profiles) {
            if (profile.getType().equals(UserProfileType.USER.getUserProfileType())) {
                userProfile = profile;
            }
        }
        return userProfile;
    }
}
