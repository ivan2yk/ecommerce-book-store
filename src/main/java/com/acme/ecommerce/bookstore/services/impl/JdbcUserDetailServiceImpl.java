package com.acme.ecommerce.bookstore.services.impl;

import com.acme.ecommerce.bookstore.entities.security.UserAuthority;
import com.acme.ecommerce.bookstore.dao.data.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by Ivan on 29/10/2018.
 */
@Service
public class JdbcUserDetailServiceImpl implements UserDetailsService {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public JdbcUserDetailServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userAccountRepository.findByUserName(s)
                .map(userAccount -> new User(userAccount.getUserName(),
                        userAccount.getPassword(),
                        userAccount.getEnabled(),
                        true,
                        true,
                        true,
                        userAccount.getUserRoles().stream()
                                .map(userRole -> new UserAuthority(userRole.getRole().getName())).collect(Collectors.toList())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s has not found", s)));
    }

}
