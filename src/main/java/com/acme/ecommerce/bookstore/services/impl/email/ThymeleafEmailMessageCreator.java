package com.acme.ecommerce.bookstore.services.impl.email;

import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Created by Ivan on 16/02/2019.
 */
@Component
@Slf4j
public class ThymeleafEmailMessageCreator extends EmailMessageCreator {

    private final TemplateEngine templateEngine;

    @Autowired
    public ThymeleafEmailMessageCreator(@Qualifier("emailTemplateEngine") TemplateEngine templateEngine,
                                        JavaMailSender javaMailSender) {
        super(javaMailSender);
        this.templateEngine = templateEngine;
    }

    @Override
    String buildBody(EmailInfo info, Map<String, Object> properties) {
        String emailTemplate = info.getEmailTemplate();

        log.info("EmailTemplate: {}", emailTemplate);

        Context context = new Context();
        context.setVariables(properties);

        return templateEngine.process(emailTemplate, context);
    }

}
