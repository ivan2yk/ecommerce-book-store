package com.acme.ecommerce.bookstore.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Ivan on 7/11/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "userPayment")
@ToString(of = {"id", "userBillingStreet1", "userBillingStreet2", "userBillingCity"})
@Entity
public class UserBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userBillingName;
    private String userBillingStreet1;
    private String userBillingStreet2;
    private String userBillingCity;
    private String userBillingState;
    private String userBillingCountry;
    private String userBillingZipcode;
    @OneToOne
    private UserPayment userPayment;

}
