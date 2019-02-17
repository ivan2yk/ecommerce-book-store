package com.acme.ecommerce.bookstore.dtos;

import lombok.Data;

/**
 * Created by Ivan on 11/02/2019.
 */
@Data
public class UserBillingDTO {

    private Long id;
    private String userBillingName;
    private String userBillingStreet1;
    private String userBillingStreet2;
    private String userBillingCity;
    private String userBillingState;
    private String userBillingCountry;
    private String userBillingZipcode;

}
