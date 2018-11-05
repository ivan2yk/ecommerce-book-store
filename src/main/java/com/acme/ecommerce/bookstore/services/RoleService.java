package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.Role;

import java.util.Optional;

/**
 * Created by Ivan on 30/10/2018.
 */
public interface RoleService {

    Optional<Role> findByName(String name);

}
