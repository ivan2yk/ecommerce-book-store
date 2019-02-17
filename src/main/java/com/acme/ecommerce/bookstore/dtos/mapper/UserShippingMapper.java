package com.acme.ecommerce.bookstore.dtos.mapper;

import com.acme.ecommerce.bookstore.dtos.UserShippingDTO;
import com.acme.ecommerce.bookstore.entities.UserShipping;
import org.mapstruct.Mapper;

/**
 * Created by Ivan on 12/02/2019.
 */
@Mapper(componentModel = "spring")
public interface UserShippingMapper extends EntityMapper<UserShippingDTO, UserShipping> {


}
