package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.dao.PasswordResetTokenDAO;
import com.acme.ecommerce.bookstore.dao.UserAccountDAO;
import com.acme.ecommerce.bookstore.dao.data.RoleRepository;
import com.acme.ecommerce.bookstore.dao.data.UserPaymentRepository;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import com.acme.ecommerce.bookstore.entities.UserPayment;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.exception.UserAlreadyExistsException;
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

    private PasswordResetTokenDAO passwordResetTokenDAO;
    private UserAccountDAO userAccountDAO;
    private RoleRepository roleRepository;
    private UserPaymentRepository userPaymentRepository;

    public UserAccountServiceImpl(PasswordResetTokenDAO passwordResetTokenDAO,
                                  UserAccountDAO userAccountDAO,
                                  RoleRepository roleRepository,
                                  UserPaymentRepository userPaymentRepository) {
        this.passwordResetTokenDAO = passwordResetTokenDAO;
        this.userAccountDAO = userAccountDAO;
        this.roleRepository = roleRepository;
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordResetToken(String token) {
        return passwordResetTokenDAO.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(UserAccount userAccount, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, userAccount);
        passwordResetTokenDAO.save(passwordResetToken);
    }

    @Override
    public Optional<UserAccount> findByUserName(String userName) {
        return userAccountDAO.findByUserName(userName);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public UserAccount createUser(UserAccount userAccount, Set<UserRole> userRoles) {
        Optional<UserAccount> byUserName = userAccountDAO.findByUserName(userAccount.getUserName());

        if (byUserName.isPresent()) {
            throw new UserAlreadyExistsException("User already exists. Nothing will be done.");
        }

        for (UserRole userRole : userRoles) {
            roleRepository.save(userRole.getRole());
        }
        userAccount.getUserRoles().addAll(userRoles);

        return userAccountDAO.save(userAccount);
    }

    @Override
    @Transactional
    public UserAccount save(UserAccount userAccount) {
        return userAccountDAO.save(userAccount);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userAccountDAO.deleteByEmail(email);
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
