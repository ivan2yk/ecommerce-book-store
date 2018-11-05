package com.acme.ecommerce.bookstore;

import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.exception.UserAlreadyExistsException;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.utils.PasswordUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class EcommerceBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(UserAccountService userAccountService) {
        return strings -> {
            UserAccount userAccount = new UserAccount();
            userAccount.setFirstName("Ivan");
            userAccount.setLastName("Leiva");
            userAccount.setUserName("i");
            userAccount.setPassword(PasswordUtils.getEncoder().encode("i"));
            userAccount.setEmail("ileiva@inclouds.biz");

            Set<UserRole> userRoles = new HashSet<>();
            Role role = new Role();
            role.setId(1L);
            role.setName("ROLE_USER");
            userRoles.add(new UserRole(role, userAccount));

            try {
                userAccountService.createUser(userAccount, userRoles);
            } catch (UserAlreadyExistsException ex) {
                //...
            }
        };
    }

}