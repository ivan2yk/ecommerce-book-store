package com.acme.ecommerce.bookstore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Ivan on 29/10/2018.
 */
public class PasswordUtils {

    public static BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String randomPassword() {
        return " ";
    }

}
