package com.pasha.findactor.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class to encrypt passwords.
 * Should be used to encrypt password for the first user with role "ADMIN".
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
public class QuickPasswordEncodingGenerator {

    private static final String PASSWORD_TO_ENCRYPT = "pasha";

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("Encrypted password: {}", passwordEncoder.encode(PASSWORD_TO_ENCRYPT));
    }
}
