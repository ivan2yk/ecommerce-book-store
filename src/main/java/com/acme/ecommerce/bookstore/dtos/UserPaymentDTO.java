package com.acme.ecommerce.bookstore.dtos;

import lombok.Data;

/**
 * Created by Ivan on 30/01/2019.
 */
@Data
public class UserPaymentDTO {

    private Long id;
    private String type;
    private String cardName;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvc;
    private String holderName;
    private Boolean defaultPayment;

}
