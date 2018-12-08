package com.acme.ecommerce.bookstore.dao.jpa;

import com.acme.ecommerce.bookstore.dao.BookDAO;
import com.acme.ecommerce.bookstore.entities.Book;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 5/12/2018.
 */
@Repository
@Profile("jpa")
public class BookDAOJpaImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            this.entityManager.persist(book);
        } else {
            this.entityManager.merge(book);
        }
        return book;
    }

    @Override
    public void deleteAll() {
        //do nothing
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("select b from Book b").getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

}
