package com.acme.ecommerce.bookstore.exception;

/**
 * Created by Ivan on 30/10/2018.
 */
public class UserAlreadyExistsException extends BookStoreException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
