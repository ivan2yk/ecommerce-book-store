package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.Book;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 7/11/2018.
 */
public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

}
