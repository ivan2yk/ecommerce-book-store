package com.acme.ecommerce.bookstore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

/**
 * Created by Ivan on 29/10/2018.
 */
public class PasswordUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private PasswordUtils() {
    }

    public static BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String randomPassword() {
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        while (salt.length() < 18) {
            int index = random.nextInt(CHARACTERS.length());
            salt.append(CHARACTERS.charAt(index));
        }
        return salt.toString();
    }

}
