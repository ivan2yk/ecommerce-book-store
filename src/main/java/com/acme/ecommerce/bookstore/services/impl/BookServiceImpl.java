package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.dao.BookDAO;
import com.acme.ecommerce.bookstore.entities.Book;
import com.acme.ecommerce.bookstore.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 7/11/2018.
 */
@Service
public class BookServiceImpl implements BookService {

    private BookDAO bookRepository;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookRepository = bookDAO;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

}
