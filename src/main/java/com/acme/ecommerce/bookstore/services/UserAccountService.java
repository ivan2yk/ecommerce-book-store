package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Ivan on 29/10/2018.
 */
public interface UserAccountService {

    Optional<PasswordResetToken> getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final UserAccount userAccount, final String token);

    Optional<UserAccount> findByUserName(String userName);

    Optional<UserAccount> findByEmail(String email);

    UserAccount createUser(UserAccount userAccount, Set<UserRole> userRoles);

    void deleteByEmail(String email);

}
