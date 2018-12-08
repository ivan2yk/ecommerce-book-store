package com.acme.ecommerce.bookstore.dao.jdbc;

import com.acme.ecommerce.bookstore.dao.BookDAO;
import com.acme.ecommerce.bookstore.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ivan on 5/12/2018.
 */
@Repository
@Profile("jdbc")
public class BookDAOJdbcImpl implements BookDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertBookJdbc;

    @Autowired
    public BookDAOJdbcImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertBookJdbc = new SimpleJdbcInsert(dataSource)
                .withTableName("book")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Book save(Book book) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        if (book.getId() == null) {
            Number newKey = this.insertBookJdbc.executeAndReturnKey(parameterSource);
            book.setId(newKey.longValue());
        } else {
            this.namedParameterJdbcTemplate.update("UPDATE SET AUTHOR=:author, CATEGORY=:category, DESCRIPTION=:description, FORMAT=:format FROM BOOK", parameterSource);
        }
        return book;
    }

    @Override
    public void deleteAll() {
        this.namedParameterJdbcTemplate.update("DELETE FROM BOOK", new HashMap<>());
    }

    @Override
    public List<Book> findAll() {
        return this.namedParameterJdbcTemplate.query("SELECT * FROM BOOK",
                new HashMap<>(),
                BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);

            Book book = this.namedParameterJdbcTemplate.queryForObject("SELECT * FROM BOOK WHERE ID = :id",
                    params,
                    BeanPropertyRowMapper.newInstance(Book.class));

            return Optional.of(book);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
