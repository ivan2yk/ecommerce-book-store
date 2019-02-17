package com.acme.ecommerce.bookstore.dtos.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ivan on 30/01/2019.
 */
@Getter
@Setter
@Builder
public class EmailInfo {

    private String emailTemplate;
    private String subject;
    private String fromAddress;
    private String messageBody;
    private EmailAttachment[] emailAttachments;
    private EmailInlineResource[] emailInlineResources;


}
