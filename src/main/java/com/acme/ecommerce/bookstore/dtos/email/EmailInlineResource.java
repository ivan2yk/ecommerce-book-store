package com.acme.ecommerce.bookstore.dtos.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ivan on 16/02/2019.
 */
@Getter
@Setter
@Builder
public class EmailInlineResource {

    private String inlineResourceName;
    private byte[] data;
    private String mimeType;

}
