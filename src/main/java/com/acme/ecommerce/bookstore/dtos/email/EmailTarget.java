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
public class EmailTarget {

    private String emailAddress;
    private String[] ccAddresses;
    private String[] bccAddresses;

}
