package com.acme.ecommerce.bookstore.entities;

import javax.persistence.*;

/**
 * Created by Ivan on 7/11/2018.
 */
@Entity
public class UserPayment {

    private Long id;
    private String type;
    private String cardName;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvc;
    private String holderName;
    private Boolean defaultPayment;
    private UserAccount userAccount;
    private UserBilling userBilling;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Boolean getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(Boolean defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
    public UserBilling getUserBilling() {
        return userBilling;
    }

    public void setUserBilling(UserBilling userBilling) {
        this.userBilling = userBilling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPayment)) return false;

        UserPayment that = (UserPayment) o;

        return getCardNumber() != null ? getCardNumber().equals(that.getCardNumber()) : that.getCardNumber() == null;
    }

    @Override
    public int hashCode() {
        return getCardNumber() != null ? getCardNumber().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserPayment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
