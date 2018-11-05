package com.acme.ecommerce.bookstore.entities.security;

import com.acme.ecommerce.bookstore.entities.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Ivan on 30/10/2018.
 */
@Entity
public class PasswordResetToken {

    private static final Integer DEFAULT_EXPIRATION_TIME_IN_MINUTES = 60 * 24;

    private Long id;
    private String token;
    private UserAccount userAccount;
    private LocalDateTime expiryDateTime;

    public PasswordResetToken() {
    }

    public PasswordResetToken(final String token, final UserAccount userAccount) {
        this.token = token;
        this.userAccount = userAccount;
        this.expiryDateTime = this.calculateExpiryDate(DEFAULT_EXPIRATION_TIME_IN_MINUTES);
    }

    private LocalDateTime calculateExpiryDate(Integer expiryTimeInMinutes) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(expiryTimeInMinutes);
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDateTime = this.calculateExpiryDate(DEFAULT_EXPIRATION_TIME_IN_MINUTES);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Column(name = "expiry_date")
    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetToken)) return false;

        PasswordResetToken that = (PasswordResetToken) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return (getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null) && (getExpiryDateTime() != null ? getExpiryDateTime().equals(that.getExpiryDateTime()) : that.getExpiryDateTime() == null);
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        result = 31 * result + (getExpiryDateTime() != null ? getExpiryDateTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", expiryDateTime=" + expiryDateTime +
                '}';
    }
}
