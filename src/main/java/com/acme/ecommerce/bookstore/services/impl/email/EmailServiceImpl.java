package com.acme.ecommerce.bookstore.services.impl.email;

import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import com.acme.ecommerce.bookstore.dtos.email.EmailTarget;
import com.acme.ecommerce.bookstore.enums.EmailProperty;
import com.acme.ecommerce.bookstore.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 13/02/2019.
 */
@Component
public class EmailServiceImpl implements EmailService {

    private EmailMessageCreator emailMessageCreator;

    @Autowired
    public EmailServiceImpl(EmailMessageCreator emailMessageCreator) {
        this.emailMessageCreator = emailMessageCreator;
    }

    @Override
    public boolean sendEmail(EmailTarget target, EmailInfo info, Map<String, Object> properties) {
        Map<String, Object> emailDataMap = new HashMap<>();
        emailDataMap.putAll(properties);
        emailDataMap.put(EmailProperty.TARGET.name(), target);
        emailDataMap.put(EmailProperty.INFO.name(), info);

        emailMessageCreator.sendMessage(emailDataMap);

        return true;
    }
}
