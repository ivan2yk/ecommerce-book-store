package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.UserBilling;
import com.acme.ecommerce.bookstore.repositories.UserBillingRepository;
import com.acme.ecommerce.bookstore.services.UserBillingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Ivan on 8/11/2018.
 */
@Service
public class UserBillingServiceImpl implements UserBillingService {

    private UserBillingRepository userBillingRepository;

    public UserBillingServiceImpl(UserBillingRepository userBillingRepository) {
        this.userBillingRepository = userBillingRepository;
    }

    @Override
    public Optional<UserBilling> findById(Long id) {
        return userBillingRepository.findById(id);
    }

}
