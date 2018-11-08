package com.acme.ecommerce.bookstore.repositories;

import com.acme.ecommerce.bookstore.entities.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ivan on 8/11/2018.
 */
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    @Query("select p from UserPayment p inner join p.userAccount a where a.id = ?1")
    List<UserPayment> findByUserAccountId(Long userId);

}
