package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.PasswordResetTokenDAO;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Ivan on 30/10/2018.
 */
public interface PasswordResetTokenRepository extends PasswordResetTokenDAO, JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findByUserAccount(UserAccount userAccount);

    Stream<PasswordResetToken> findAllByExpiryDateTimeLessThan(LocalDateTime now);

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDateTime <= ?1")
    void deleteAllExpiredSince(LocalDateTime now);

}
