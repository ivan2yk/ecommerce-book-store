package com.acme.ecommerce.bookstore.dao;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Ivan on 5/12/2018.
 */
public interface PasswordResetTokenDAO {

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findByUserAccount(UserAccount userAccount);

    Stream<PasswordResetToken> findAllByExpiryDateTimeLessThan(LocalDateTime now);

    void deleteAllExpiredSince(LocalDateTime now);

    void deleteAll();

}
