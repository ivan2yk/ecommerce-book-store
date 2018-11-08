package com.acme.ecommerce.bookstore.repositories;

import com.acme.ecommerce.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ivan on 7/11/2018.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
