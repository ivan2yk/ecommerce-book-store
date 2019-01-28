package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.UserShippingDAO;
import com.acme.ecommerce.bookstore.entities.UserShipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ivan on 13/12/2018.
 */
@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long>, UserShippingDAO {

    @Query("select s from UserShipping s inner join s.userAccount a where a.id = ?1")
    List<UserShipping> findByUserAccountId(Long id);

    void deleteById(Long id);

}
