package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.dao.data.RoleRepository;
import com.acme.ecommerce.bookstore.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Ivan on 30/10/2018.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

}
