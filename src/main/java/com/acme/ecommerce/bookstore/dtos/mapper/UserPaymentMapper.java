package com.acme.ecommerce.bookstore.dtos.mapper;

import com.acme.ecommerce.bookstore.dtos.UserPaymentDTO;
import com.acme.ecommerce.bookstore.entities.UserPayment;
import org.mapstruct.Mapper;

/**
 * Created by Ivan on 11/02/2019.
 */
@Mapper(componentModel = "spring")
public interface UserPaymentMapper extends EntityMapper<UserPaymentDTO, UserPayment> {


}