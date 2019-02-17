package com.acme.ecommerce.bookstore.services.impl.email;

import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import com.acme.ecommerce.bookstore.dtos.email.EmailInlineResource;
import com.acme.ecommerce.bookstore.dtos.email.EmailTarget;
import com.acme.ecommerce.bookstore.services.EmailService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 16/02/2019.
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "data"})
public class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void whenSendEmail_thenOk() throws Exception {
        EmailTarget emailTarget = EmailTarget.builder()
                .emailAddress("leivagarcia18@gmail.com")
                .build();

        ClassPathResource classPathResource = new ClassPathResource("static/image/logo.png");
        InputStream inputStream = classPathResource.getInputStream();
        byte[] bytes = StreamUtils.copyToByteArray(inputStream);

        EmailInlineResource logo = EmailInlineResource.builder()
                .inlineResourceName("logo")
                .data(bytes)
                .mimeType("image/png")
                .build();

        EmailInfo emailInfo = EmailInfo.builder()
                .emailTemplate("mail/email-inlinemessage.html")
                .fromAddress("ecommercesolution@acme.com")
                .subject("Hello from ecommerce solution")
                .emailInlineResources(new EmailInlineResource[]{logo})
                .build();

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Ivan Luis");
        properties.put("subscriptionDate", new Date());
        properties.put("hobbies", new String[]{"music", "sex", "reading"});
        properties.put("imageResourceName", "logo");

        emailService.sendEmail(emailTarget, emailInfo, properties);
    }

}