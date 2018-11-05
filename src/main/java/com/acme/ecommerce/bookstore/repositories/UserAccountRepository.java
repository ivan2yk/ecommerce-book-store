package com.acme.ecommerce.bookstore.repositories;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Ivan on 29/10/2018.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String userName);

    Optional<UserAccount> findByEmail(String email);

}
