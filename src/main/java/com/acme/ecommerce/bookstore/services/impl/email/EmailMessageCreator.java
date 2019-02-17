package com.acme.ecommerce.bookstore.services.impl.email;

import com.acme.ecommerce.bookstore.dtos.email.EmailAttachment;
import com.acme.ecommerce.bookstore.dtos.email.EmailInfo;
import com.acme.ecommerce.bookstore.dtos.email.EmailInlineResource;
import com.acme.ecommerce.bookstore.dtos.email.EmailTarget;
import com.acme.ecommerce.bookstore.enums.EmailProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.util.ByteArrayDataSource;
import java.util.Map;

/**
 * Created by Ivan on 13/02/2019.
 */
@Slf4j
public abstract class EmailMessageCreator {

    private JavaMailSender javaMailSender;

    public EmailMessageCreator(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(Map<String, Object> properties) {
        MimeMessagePreparator mimeMessagePreparator = this.buildMimeMessagePreparator(properties);
        this.javaMailSender.send(mimeMessagePreparator);
    }

    private MimeMessagePreparator buildMimeMessagePreparator(Map<String, Object> properties) {
        return mimeMessage -> {
            EmailTarget emailTarget = (EmailTarget) properties.get(EmailProperty.TARGET.name());
            EmailInfo emailInfo = (EmailInfo) properties.get(EmailProperty.INFO.name());
            boolean isMultipart = arrayHasContent(emailInfo.getEmailAttachments()) || arrayHasContent(emailInfo.getEmailInlineResources());

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
            mimeMessageHelper.setTo(emailTarget.getEmailAddress());
            mimeMessageHelper.setFrom(emailInfo.getFromAddress());
            mimeMessageHelper.setSubject(emailInfo.getSubject());

            if (arrayHasContent(emailTarget.getBccAddresses())) {
                mimeMessageHelper.setBcc(emailTarget.getBccAddresses());
            }
            if (arrayHasContent(emailTarget.getCcAddresses())) {
                mimeMessageHelper.setCc(emailTarget.getCcAddresses());
            }

            String messageBody = emailInfo.getMessageBody();
            if (messageBody == null) {
                messageBody = buildBody(emailInfo, properties);
            }

            mimeMessageHelper.setText(messageBody, true);

            if (arrayHasContent(emailInfo.getEmailAttachments())) {
                for (EmailAttachment emailAttachment : emailInfo.getEmailAttachments()) {
                    ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(emailAttachment.getData(), emailAttachment.getMimeType());
                    mimeMessageHelper.addAttachment(emailAttachment.getFilename(), byteArrayDataSource);
                }
            }

            if (arrayHasContent(emailInfo.getEmailInlineResources())) {
                for (EmailInlineResource emailInlineResource : emailInfo.getEmailInlineResources()) {
                    InputStreamSource byteArrayResource = new ByteArrayResource(emailInlineResource.getData());
                    mimeMessageHelper.addInline(emailInlineResource.getInlineResourceName(), byteArrayResource, emailInlineResource.getMimeType());
                }
            }

        };
    }

    abstract String buildBody(EmailInfo info, Map<String, Object> properties);

    private boolean arrayHasContent(Object[] objects) {
        return objects != null && objects.length > 0;
    }

}
