package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.BookDAO;
import com.acme.ecommerce.bookstore.entities.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ivan on 5/12/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(value = {"test", "data"})
public class BookRepositoryTest {

    @Autowired
    private BookDAO bookDAO;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        Book book1 = Book.builder()
                .title("The Martian")
                .author("Andy Weir")
                .publisher("Atlas")
                .language("English").build();
        bookDAO.save(book1);

        Book book2 = Book.builder()
                .title("The Hobbit")
                .author("J. Tolkien")
                .publisher("Alfaguara")
                .language("English").build();
        bookDAO.save(book2);

        Book book3 = Book.builder()
                .title("Domain Driven Design")
                .author("Erick Evans")
                .publisher("Alfaguara")
                .language("English").build();
        bookDAO.save(book3);
    }

    @After
    public void tearDown() {
        this.bookDAO.deleteAll();
    }

    @Test
    public void whenSave_ThenOk() {
        Book book = Book.builder()
                .title("Book Title")
                .author("Author")
                .language("Spanish")
                .description("Description").build();

        this.bookDAO.save(book);
        Optional<Book> newBookOptional = this.bookDAO.findById(book.getId());

        assertNotNull(book.getId());
        assertTrue(newBookOptional.isPresent());
        Book newBook = newBookOptional.get();
        assertEquals(book.getTitle(), newBook.getTitle());
    }

    @Test
    public void whenDeleteAll_thenOk() {
        this.bookDAO.deleteAll();
        List<Book> all = this.bookDAO.findAll();
        assertEquals(0, all.size());
    }

    @Test
    public void whenGetAll_thenOk() {
        List<Book> books = bookDAO.findAll();
        logger.info("Books: {}", books);

        assertEquals(3, books.size());
    }

    @Test
    public void whenFindById_thenOk() {
        Optional<Book> bookOptional = bookDAO.findById(1L);

        assertTrue(bookOptional.isPresent());
        logger.info("Book: {}", bookOptional.get());
        assertEquals("The Martian", bookOptional.get().getTitle());
    }


}