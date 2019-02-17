package com.acme.ecommerce.bookstore.dtos;

import lombok.Data;

/**
 * Created by Ivan on 30/01/2019.
 */
@Data
public class UserShippingDTO {

    private Long id;
    private String userShippingName;
    private String userShippingStreet1;
    private String userShippingStreet2;
    private String userShippingCity;
    private String userShippingState;
    private String userShippingCountry;
    private String userShippingZipcode;
    private Boolean userShippingDefault;

}
