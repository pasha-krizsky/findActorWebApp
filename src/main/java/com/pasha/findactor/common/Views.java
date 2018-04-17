package com.pasha.findactor.common;

/**
 * This class contains all views.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public final class Views {
    private Views() {
    }

    /* Names of views are here */
    public static final String USERS_LIST = "users_list_admin";
    public static final String REGISTRATION = "registration";
    public static final String REGISTRATION_SUCCESS = "registration_success";
    public static final String ACCESS_DENIED = "access_denied";
    public static final String LOGIN = "login";
    public static final String LIST_WORKSHEETS_AGENT = "list_worksheets_agent";
    public static final String LIST_WORKSHEETS_DIRECTOR = "list_worksheets_director";
    public static final String LIST_WORKSHEETS_USER = "list_worksheets_user";
    public static final String SUBMIT_NEW_WORKSHEET = "submit_new_worksheet_user";
    public static final String WORKSHEET_SUBMITTED = "worksheet_submitted_user";
    public static final String WORKSHEET_EDIT = "worksheet_edit";
    public static final String WORKSHEET = "worksheet_view";

    /* Names of redirect urls are here */
    public static final String REDIRECT_LIST = "redirect:/listUsers";
    public static final String REDIRECT_WORKSHEETS_USER = "redirect:/worksheetsUser";
    public static final String REDIRECT_SUBMIT_WORKSHEET = "redirect:/submitWorksheet";
    public static final String REDIRECT_LOGIN_LOGOUT = "redirect:/login?logout";
    public static final String REDIRECT_WORKSHEETS_AGENT = "redirect:/worksheetsAgent";
    public static final String REDIRECT_WORKSHEETS_DIRECTOR = "redirect:/worksheetsDirector";
}
