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
@EqualsAndHashCode(of = "cardNumber")
@ToString(of = {"id", "type", "cardName"})
@Entity
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String cardName;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvc;
    private String holderName;
    private Boolean defaultPayment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
    private UserBilling userBilling;

}
