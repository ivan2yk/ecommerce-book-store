package com.acme.ecommerce.bookstore;

import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.enums.RoleEnum;
import com.acme.ecommerce.bookstore.exception.UserAlreadyExistsException;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class EcommerceBookStoreApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(UserAccountService userAccountService) {
        return strings -> {
            Optional<UserAccount> userAccountOptional = userAccountService.findByUserName("i");

            if (!userAccountOptional.isPresent()) {
                UserAccount userAccount = new UserAccount();
                userAccount.setFirstName("Ivan");
                userAccount.setLastName("Leiva");
                userAccount.setUserName("i");
                userAccount.setPassword(PasswordUtils.getEncoder().encode("i"));
                userAccount.setEmail("ileiva@acme.com");

                Set<UserRole> userRoles = new HashSet<>();
                Role role = new Role();
                role.setId(1L);
                role.setName(RoleEnum.ROLE_USER.toString());
                userRoles.add(new UserRole(role, userAccount));

                try {
                    userAccountService.deleteByEmail(userAccount.getEmail());
                    userAccountService.createUser(userAccount, userRoles);
                } catch (UserAlreadyExistsException ex) {
                    logger.error(ex.getMessage(), ex.getCause());
                }
            }
        };
    }

}