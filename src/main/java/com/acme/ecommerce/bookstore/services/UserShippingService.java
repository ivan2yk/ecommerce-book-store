package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.UserShipping;

import java.util.Optional;

/**
 * Created by Ivan on 13/12/2018.
 */
public interface UserShippingService {

    Optional<UserShipping> findById(Long id);

    void deleteById(Long userShippingId);

}
