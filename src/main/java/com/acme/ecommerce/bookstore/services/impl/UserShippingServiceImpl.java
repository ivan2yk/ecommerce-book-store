package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.dao.UserShippingDAO;
import com.acme.ecommerce.bookstore.entities.UserShipping;
import com.acme.ecommerce.bookstore.services.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Ivan on 13/12/2018.
 */
@Service
public class UserShippingServiceImpl implements UserShippingService {

    private UserShippingDAO userShippingDAO;

    @Autowired
    public UserShippingServiceImpl(UserShippingDAO userShippingDAO) {
        this.userShippingDAO = userShippingDAO;
    }

    @Override
    public Optional<UserShipping> findById(Long id) {
        return userShippingDAO.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long userShippingId) {
        userShippingDAO.deleteById(userShippingId);
    }

}
