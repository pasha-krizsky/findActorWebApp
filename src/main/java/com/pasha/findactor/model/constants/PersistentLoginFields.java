package com.pasha.findactor.model.constants;

/**
 * This class contains names of fields of {@link Tables#PERSISTENT_LOGINS} table.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public final class PersistentLoginFields {
    private PersistentLoginFields() {
    }

    public static final String USERNAME = "username";
    public static final String TOKEN = "token";
    public static final String LAST_USED = "LAST_USED";
}
