package com.acme.ecommerce.bookstore.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

/**
 * Created by Ivan on 7/11/2018.
 */
public class PasswordUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);

    @Test
    public void getEncoder() throws Exception {
        BCryptPasswordEncoder encoder = PasswordUtils.getEncoder();
        assertNotNull(encoder);
    }

    @Test
    public void randomPassword() throws Exception {
        String pass1 = PasswordUtils.randomPassword();
        LOGGER.info(pass1);
        assertNotNull(pass1);
    }

}