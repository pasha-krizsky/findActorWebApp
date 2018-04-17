package com.pasha.findactor.common;

/**
 * This class contains all urls and url patterns.
 * All new urls should be added here.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public final class Urls {
    private Urls() {
    }

    /* Patterns for some urls to use in Security Configuration */
    public static final String DELETE_USER_URL_PATTERN = "/deleteUser-*";
    public static final String EDIT_USER_URL_PATTERN = "/editUser-*";
    public static final String VIEW_WORKSHEET_PATTERN = "/viewWorksheet-*";
    public static final String EDIT_WORKSHEET_PATTERN = "/editWorksheet-{worksheetId}";
    public static final String CASTING_WORKSHEET_PATTERN = "/castingWorksheet-{worksheetId}";
    public static final String OFFER_WORKSHEET_PATTERN = "offerWorksheet-{worksheetId}";
    public static final String DECLINE_WORKSHEET_DIRECTOR_PATTERN = "declineWorksheetDirector-{worksheetId}";
    public static final String DECLINE_WORKSHEET_AGENT_PATTERN = "declineWorksheetAgent-{worksheetId}";

    /* All urls are here */
    public static final String WORKSHEETS_USER_URL = "/worksheetsUser";
    public static final String WORKSHEETS_AGENT_URL = "/worksheetsAgent";
    public static final String WORKSHEETS_DIRECTOR_URL = "/worksheetsDirector";

    public static final String SUBMIT_NEW_WORKSHEET_URL = "/submitWorksheet";
    public static final String ROOT_URL = "/";
    public static final String WELCOME_URL = "/welcome";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String REGISTER_USER_URL = "/registerUser";
    public static final String LIST_USERS_URL = "/listUsers";
    public static final String ACCESS_DENIED_URL = "/accessDenied";

    /* All urls with parameters are here */
    public static final String EDIT_USER_URL = "/editUser-{ssoId}";
    public static final String DELETE_USER_URL = "/deleteUser-{ssoId}";
    public static final String VIEW_WORKSHEET = "/viewWorksheet-{worksheetId}";
    public static final String EDIT_WORKSHEET = "/editWorksheet-{worksheetId}";
    public static final String CASTING_WORKSHEET = "/castingWorksheet-{worksheetId}";
    public static final String OFFER_WORKSHEET = "offerWorksheet-{worksheetId}";
    public static final String DECLINE_WORKSHEET_DIRECTOR = "declineWorksheetDirector-{worksheetId}";
    public static final String DECLINE_WORKSHEET_AGENT = "declineWorksheetAgent-{worksheetId}";
}
