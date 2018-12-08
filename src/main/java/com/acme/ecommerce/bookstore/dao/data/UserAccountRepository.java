package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.UserAccountDAO;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Ivan on 29/10/2018.
 */
public interface UserAccountRepository extends UserAccountDAO, JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String userName);

    Optional<UserAccount> findByEmail(String email);

    void deleteByEmail(String email);

}
