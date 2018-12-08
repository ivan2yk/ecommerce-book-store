package com.acme.ecommerce.bookstore.dao;

import com.acme.ecommerce.bookstore.entities.Book;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 4/12/2018.
 */
public interface BookDAO {

    Book save(Book book);

    void deleteAll();

    List<Book> findAll();

    Optional<Book> findById(Long id);

}
