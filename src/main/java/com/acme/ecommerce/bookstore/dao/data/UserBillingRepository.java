package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.UserBillingDAO;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ivan on 8/11/2018.
 */
public interface UserBillingRepository extends UserBillingDAO, JpaRepository<UserBilling, Long> {

}
