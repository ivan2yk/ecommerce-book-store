package com.acme.ecommerce.bookstore.entities;

import javax.persistence.*;

/**
 * Created by Ivan on 7/11/2018.
 */
@Entity
public class UserBilling {

    private Long id;
    private String userBillingName;
    private String userBillingStreet1;
    private String userBillingStreet2;
    private String userBillingCity;
    private String userBillingState;
    private String userBillingCountry;
    private String userBillingZipcode;
    private UserPayment userPayment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserBillingName() {
        return userBillingName;
    }

    public void setUserBillingName(String userBillingName) {
        this.userBillingName = userBillingName;
    }

    public String getUserBillingStreet1() {
        return userBillingStreet1;
    }

    public void setUserBillingStreet1(String userBillingStreet1) {
        this.userBillingStreet1 = userBillingStreet1;
    }

    public String getUserBillingStreet2() {
        return userBillingStreet2;
    }

    public void setUserBillingStreet2(String userBillingStreet2) {
        this.userBillingStreet2 = userBillingStreet2;
    }

    public String getUserBillingCity() {
        return userBillingCity;
    }

    public void setUserBillingCity(String userBillingCity) {
        this.userBillingCity = userBillingCity;
    }

    public String getUserBillingState() {
        return userBillingState;
    }

    public void setUserBillingState(String userBillingState) {
        this.userBillingState = userBillingState;
    }

    public String getUserBillingCountry() {
        return userBillingCountry;
    }

    public void setUserBillingCountry(String userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
    }

    public String getUserBillingZipcode() {
        return userBillingZipcode;
    }

    public void setUserBillingZipcode(String userBillingZipcode) {
        this.userBillingZipcode = userBillingZipcode;
    }

    @OneToOne
    public UserPayment getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(UserPayment userPayment) {
        this.userPayment = userPayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBilling)) return false;

        UserBilling that = (UserBilling) o;

        if (getUserBillingName() != null ? !getUserBillingName().equals(that.getUserBillingName()) : that.getUserBillingName() != null)
            return false;
        if (getUserBillingStreet1() != null ? !getUserBillingStreet1().equals(that.getUserBillingStreet1()) : that.getUserBillingStreet1() != null)
            return false;
        if (getUserBillingStreet2() != null ? !getUserBillingStreet2().equals(that.getUserBillingStreet2()) : that.getUserBillingStreet2() != null)
            return false;
        if (getUserBillingCity() != null ? !getUserBillingCity().equals(that.getUserBillingCity()) : that.getUserBillingCity() != null)
            return false;
        if (getUserBillingState() != null ? !getUserBillingState().equals(that.getUserBillingState()) : that.getUserBillingState() != null)
            return false;
        return getUserBillingCountry() != null ? getUserBillingCountry().equals(that.getUserBillingCountry()) : that.getUserBillingCountry() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserBillingName() != null ? getUserBillingName().hashCode() : 0;
        result = 31 * result + (getUserBillingStreet1() != null ? getUserBillingStreet1().hashCode() : 0);
        result = 31 * result + (getUserBillingStreet2() != null ? getUserBillingStreet2().hashCode() : 0);
        result = 31 * result + (getUserBillingCity() != null ? getUserBillingCity().hashCode() : 0);
        result = 31 * result + (getUserBillingState() != null ? getUserBillingState().hashCode() : 0);
        result = 31 * result + (getUserBillingCountry() != null ? getUserBillingCountry().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserBilling{" +
                "id=" + id +
                ", userBillingName='" + userBillingName + '\'' +
                '}';
    }
}
