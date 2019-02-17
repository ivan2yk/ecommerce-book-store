package com.acme.ecommerce.bookstore.dtos.mapper;

import java.util.List;

/**
 * Created by Ivan on 11/02/2019.
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

}
