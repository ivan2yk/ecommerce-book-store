package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.exception.UserAlreadyExistsException;
import com.acme.ecommerce.bookstore.repositories.PasswordResetTokenRepository;
import com.acme.ecommerce.bookstore.repositories.RoleRepository;
import com.acme.ecommerce.bookstore.repositories.UserAccountRepository;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Ivan on 30/10/2018.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserAccountRepository userAccountRepository;
    private RoleRepository roleRepository;

    public UserAccountServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository,
                                  UserAccountRepository userAccountRepository,
                                  RoleRepository roleRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(UserAccount userAccount, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, userAccount);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public Optional<UserAccount> findByUserName(String userName) {
        return userAccountRepository.findByUserName(userName);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount createUser(UserAccount userAccount, Set<UserRole> userRoles) {
        Optional<UserAccount> byUserName = userAccountRepository.findByUserName(userAccount.getUserName());

        if (byUserName.isPresent()) {
            throw new UserAlreadyExistsException("User already exists. Nothing will be done.");
        }

        for (UserRole userRole : userRoles) {
            roleRepository.save(userRole.getRole());
        }
        userAccount.getUserRoles().addAll(userRoles);

        return userAccountRepository.save(userAccount);
    }

}
