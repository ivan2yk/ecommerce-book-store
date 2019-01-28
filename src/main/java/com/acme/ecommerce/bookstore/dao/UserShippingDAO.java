package com.acme.ecommerce.bookstore.dao;

import com.acme.ecommerce.bookstore.entities.UserShipping;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 13/12/2018.
 */
public interface UserShippingDAO {

    UserShipping save(UserShipping userShipping);

    Optional<UserShipping> findById(Long id);

    List<UserShipping> findByUserAccountId(Long id);

    void deleteById(Long id);

}
