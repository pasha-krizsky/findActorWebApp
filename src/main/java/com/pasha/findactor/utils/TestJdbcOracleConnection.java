package com.pasha.findactor.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for checking connection to database.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
public final class TestJdbcOracleConnection {

    private static final String DRIVER_NOT_FOUND_MSG = "DRIVER NOT FOUND!";
    private static final String DRIVER_FOUND_MSG = "DRIVER FOUND!";
    private static final String CONNECTED_MSG = "CONNECTED!";
    private static final String ERROR_MSG = "ERROR";

    private static final String DRIVER_TO_CHECK = "oracle.jdbc.driver.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/orcl";
    private static final String USER = "pasha";
    private static final String PASSWORD = "pasha";

    public static void main(String[] args) {
        try {
            Class.forName(DRIVER_TO_CHECK);
            log.info(DRIVER_FOUND_MSG);
        } catch (ClassNotFoundException e) {
            log.error(DRIVER_NOT_FOUND_MSG);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            log.info(CONNECTED_MSG);
        } catch (SQLException e) {
            log.error(ERROR_MSG, e);
        }
    }
}
