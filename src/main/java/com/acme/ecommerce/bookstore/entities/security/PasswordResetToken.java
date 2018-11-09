package com.acme.ecommerce.bookstore.entities.security;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Ivan on 30/10/2018.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = "userAccount")
@ToString(exclude = "userAccount")
@Entity
public class PasswordResetToken {

    private static final Integer DEFAULT_EXPIRATION_TIME_IN_MINUTES = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    @Column(name = "expiry_date")
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

}