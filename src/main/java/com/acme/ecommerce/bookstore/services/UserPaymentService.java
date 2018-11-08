package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.UserPayment;

import java.util.Optional;

/**
 * Created by Ivan on 8/11/2018.
 */
public interface UserPaymentService {

    Optional<UserPayment> findById(Long id);

    void deleteById(Long id);

}
