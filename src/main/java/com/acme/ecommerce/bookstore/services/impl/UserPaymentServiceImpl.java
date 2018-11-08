package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.UserPayment;
import com.acme.ecommerce.bookstore.repositories.UserPaymentRepository;
import com.acme.ecommerce.bookstore.services.UserPaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Ivan on 8/11/2018.
 */
@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private UserPaymentRepository userPaymentRepository;

    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public Optional<UserPayment> findById(Long id) {
        return userPaymentRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userPaymentRepository.deleteById(id);
    }

}
