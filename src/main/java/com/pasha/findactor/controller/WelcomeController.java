package com.pasha.findactor.controller;

import com.pasha.findactor.common.Urls;
import com.pasha.findactor.common.Views;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Main controller of the application.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Controller
@RequestMapping(Urls.ROOT_URL)
@SessionAttributes("roles")
public class WelcomeController extends AbstractController {

    private static final String LOGGED_IN_USER_ATTR = "loggedinuser";

    @RequestMapping(value = {Urls.ROOT_URL, Urls.WELCOME_URL}, method = RequestMethod.GET)
    public String welcome() {
        return Views.REDIRECT_LIST;
    }

    /**
     * This method handles access-denied redirect.
     */
    @RequestMapping(value = Urls.ACCESS_DENIED_URL, method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());
        return Views.ACCESS_DENIED;
    }
}