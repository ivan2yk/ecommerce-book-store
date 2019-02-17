package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import com.acme.ecommerce.bookstore.dtos.email.EmailTarget;

import java.util.Map;

/**
 * Created by Ivan on 6/11/2018.
 */
public interface EmailService {

    boolean sendEmail(EmailTarget target, EmailInfo info, Map<String, Object> properties);

}