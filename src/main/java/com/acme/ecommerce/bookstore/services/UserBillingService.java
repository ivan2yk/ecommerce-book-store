package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.UserBilling;

import java.util.Optional;

/**
 * Created by Ivan on 8/11/2018.
 */
public interface UserBillingService {

    Optional<UserBilling> findById(Long id);

}
