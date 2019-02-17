package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import com.acme.ecommerce.bookstore.dtos.email.EmailTarget;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 17/02/2019.
 */
@Component
@Slf4j
public class EmailBuilderService {

    private EmailService emailService;

    public EmailBuilderService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void constructResetTokenEmail(UserAccount userAccount, String password, String url, String token) {
        EmailTarget emailTarget = EmailTarget.builder()
                .emailAddress(userAccount.getEmail())
                .build();

        EmailInfo emailInfo = EmailInfo.builder()
                .emailTemplate("mail/email-newuser")
                .subject("Le's Bookstore - New User")
                .fromAddress("lesbookstore@acme.com")
                .build();

        String verifyLink = String.format("%s/newUser?token=%s", url, token);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", userAccount.getFirstName());
        objectMap.put("verifyLink", verifyLink);
        objectMap.put("password", password);

        emailService.sendEmail(emailTarget, emailInfo, objectMap);
    }

    public void constructResetPasswordTokenEmail(UserAccount userAccount, String password, String url, String token) {
        EmailTarget emailTarget = EmailTarget.builder()
                .emailAddress(userAccount.getEmail())
                .build();

        EmailInfo emailInfo = EmailInfo.builder()
                .emailTemplate("mail/email-resetpassword")
                .subject("Le's Bookstore - Reset Password")
                .fromAddress("lesbookstore@acme.com")
                .build();

        String verifyLink = String.format("%s/newUser?token=%s", url, token);

        log.info("VerifyLink: {}", verifyLink);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", userAccount.getFirstName());
        objectMap.put("verifyLink", verifyLink);
        objectMap.put("password", password);

        emailService.sendEmail(emailTarget, emailInfo, objectMap);
    }


}
