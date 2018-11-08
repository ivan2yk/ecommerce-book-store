package com.acme.ecommerce.bookstore.entities;

import javax.persistence.*;

/**
 * Created by Ivan on 7/11/2018.
 */
@Entity
public class UserShipping {

    private Long id;
    private String userShippingName;
    private String userShippingStreet1;
    private String userShippingStreet2;
    private String userShippingCity;
    private String userShippingState;
    private String userShippingCountry;
    private String userShippingZipcode;
    private UserAccount userAccount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserShippingName() {
        return userShippingName;
    }

    public void setUserShippingName(String userShippingName) {
        this.userShippingName = userShippingName;
    }

    public String getUserShippingStreet1() {
        return userShippingStreet1;
    }

    public void setUserShippingStreet1(String userShippingStreet1) {
        this.userShippingStreet1 = userShippingStreet1;
    }

    public String getUserShippingStreet2() {
        return userShippingStreet2;
    }

    public void setUserShippingStreet2(String userShippingStreet2) {
        this.userShippingStreet2 = userShippingStreet2;
    }

    public String getUserShippingCity() {
        return userShippingCity;
    }

    public void setUserShippingCity(String userShippingCity) {
        this.userShippingCity = userShippingCity;
    }

    public String getUserShippingState() {
        return userShippingState;
    }

    public void setUserShippingState(String userShippingState) {
        this.userShippingState = userShippingState;
    }

    public String getUserShippingCountry() {
        return userShippingCountry;
    }

    public void setUserShippingCountry(String userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
    }

    public String getUserShippingZipcode() {
        return userShippingZipcode;
    }

    public void setUserShippingZipcode(String userShippingZipcode) {
        this.userShippingZipcode = userShippingZipcode;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserShipping)) return false;

        UserShipping that = (UserShipping) o;

        if (getUserShippingName() != null ? !getUserShippingName().equals(that.getUserShippingName()) : that.getUserShippingName() != null)
            return false;
        if (getUserShippingStreet1() != null ? !getUserShippingStreet1().equals(that.getUserShippingStreet1()) : that.getUserShippingStreet1() != null)
            return false;
        if (getUserShippingStreet2() != null ? !getUserShippingStreet2().equals(that.getUserShippingStreet2()) : that.getUserShippingStreet2() != null)
            return false;
        if (getUserShippingCity() != null ? !getUserShippingCity().equals(that.getUserShippingCity()) : that.getUserShippingCity() != null)
            return false;
        return getUserShippingState() != null ? getUserShippingState().equals(that.getUserShippingState()) : that.getUserShippingState() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserShippingName() != null ? getUserShippingName().hashCode() : 0;
        result = 31 * result + (getUserShippingStreet1() != null ? getUserShippingStreet1().hashCode() : 0);
        result = 31 * result + (getUserShippingStreet2() != null ? getUserShippingStreet2().hashCode() : 0);
        result = 31 * result + (getUserShippingCity() != null ? getUserShippingCity().hashCode() : 0);
        result = 31 * result + (getUserShippingState() != null ? getUserShippingState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserShipping{" +
                "userShippingName='" + userShippingName + '\'' +
                ", userShippingStreet1='" + userShippingStreet1 + '\'' +
                ", userShippingStreet2='" + userShippingStreet2 + '\'' +
                '}';
    }
}
