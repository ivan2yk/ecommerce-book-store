package com.acme.ecommerce.bookstore.dao;

import com.acme.ecommerce.bookstore.entities.UserAccount;

import java.util.Optional;

/**
 * Created by Ivan on 5/12/2018.
 */
public interface UserAccountDAO {

    UserAccount save(UserAccount userAccount);

    Optional<UserAccount> findByUserName(String userName);

    Optional<UserAccount> findByEmail(String email);

    void deleteByEmail(String email);

    void deleteAll();

}
