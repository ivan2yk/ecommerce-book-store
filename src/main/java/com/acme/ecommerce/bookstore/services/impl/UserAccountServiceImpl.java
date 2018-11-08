package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import com.acme.ecommerce.bookstore.entities.UserPayment;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.exception.UserAlreadyExistsException;
import com.acme.ecommerce.bookstore.repositories.PasswordResetTokenRepository;
import com.acme.ecommerce.bookstore.repositories.RoleRepository;
import com.acme.ecommerce.bookstore.repositories.UserAccountRepository;
import com.acme.ecommerce.bookstore.repositories.UserPaymentRepository;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private UserPaymentRepository userPaymentRepository;

    public UserAccountServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository,
                                  UserAccountRepository userAccountRepository,
                                  RoleRepository roleRepository,
                                  UserPaymentRepository userPaymentRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.userPaymentRepository = userPaymentRepository;
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
    @Transactional
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

    @Override
    @Transactional
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userAccountRepository.deleteByEmail(email);
    }

    @Override
    @Transactional
    public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, UserAccount userAccount) {
        userPayment.setUserAccount(userAccount);
        userPayment.setUserBilling(userBilling);
        userPayment.setDefaultPayment(true);
        userBilling.setUserPayment(userPayment);
        userAccount.getUserPayments().add(userPayment);
        this.save(userAccount);
    }

    @Override
    @Transactional
    public void setUserDefaultPayment(Long idDefaultPaymentId, UserAccount userAccount) {
        Long userAccountId = userAccount.getId();
        List<UserPayment> userPayments = userPaymentRepository.findByUserAccountId(userAccountId);

        for (UserPayment userPayment : userPayments) {
            if (userPayment.getId().equals(idDefaultPaymentId)) {
                userPayment.setDefaultPayment(true);
                userPaymentRepository.save(userPayment);
            } else {
                userPayment.setDefaultPayment(false);
                userPaymentRepository.save(userPayment);
            }
        }
    }

}
