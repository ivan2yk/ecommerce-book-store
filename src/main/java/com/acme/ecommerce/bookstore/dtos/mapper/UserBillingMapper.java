package com.acme.ecommerce.bookstore.dtos.mapper;

import com.acme.ecommerce.bookstore.dtos.UserBillingDTO;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import org.mapstruct.Mapper;

/**
 * Created by Ivan on 11/02/2019.
 */
@Mapper(componentModel = "spring")
public interface UserBillingMapper extends EntityMapper<UserBillingDTO, UserBilling> {

}
