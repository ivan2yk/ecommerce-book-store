package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.BookDAO;
import com.acme.ecommerce.bookstore.entities.Book;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ivan on 7/11/2018.
 */
@Profile("data")
public interface BookRepository extends BookDAO, JpaRepository<Book, Long> {

}
