package com.acme.ecommerce.bookstore.exception;

/**
 * Created by Ivan on 30/10/2018.
 */
public class BookStoreException extends RuntimeException {

    public BookStoreException(String message) {
        super(message);
    }

    public BookStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
