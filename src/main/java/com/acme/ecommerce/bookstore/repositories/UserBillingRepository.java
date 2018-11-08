package com.acme.ecommerce.bookstore.repositories;

import com.acme.ecommerce.bookstore.entities.UserBilling;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ivan on 8/11/2018.
 */
public interface UserBillingRepository extends JpaRepository<UserBilling, Long> {

}
