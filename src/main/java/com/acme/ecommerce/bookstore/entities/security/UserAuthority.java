package com.acme.ecommerce.bookstore.entities.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Ivan on 29/10/2018.
 */
public class UserAuthority implements GrantedAuthority {

    private final String authority;

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
