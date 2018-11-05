package com.acme.ecommerce.bookstore.repositories;

import com.acme.ecommerce.bookstore.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Ivan on 30/10/2018.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
