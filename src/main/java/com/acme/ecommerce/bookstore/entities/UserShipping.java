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
@EqualsAndHashCode(exclude = "userAccount")
@ToString(exclude = "userAccount")
@Entity
public class UserShipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userShippingName;
    private String userShippingStreet1;
    private String userShippingStreet2;
    private String userShippingCity;
    private String userShippingState;
    private String userShippingCountry;
    private String userShippingZipcode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

}
