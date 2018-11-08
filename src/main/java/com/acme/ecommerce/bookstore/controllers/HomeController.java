package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.entities.Book;
import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.services.BookService;
import com.acme.ecommerce.bookstore.services.RoleService;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * Created by Ivan on 29/10/2018.
 */
@Controller
public class HomeController {

    private UserDetailsService jdbcSecurityService;
    private UserAccountService userAccountService;
    private RoleService roleService;
    private BookService bookService;

    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
    private static final String NO_ROLE_PRESENT_MESSAGE = "No role defined";
    private static final String ROLE_USER = "ROLE_USER";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HomeController(@Qualifier("jdbcUserDetailServiceImpl") UserDetailsService userDetailsService,
                          UserAccountService userAccountService,
                          RoleService roleService,
                          BookService bookService) {
        this.jdbcSecurityService = userDetailsService;
        this.userAccountService = userAccountService;
        this.roleService = roleService;
        this.bookService = bookService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }

    @RequestMapping("/bookshelf")
    public String bookshelf(Model model) {
        List<Book> allBooks = bookService.findAll();

        model.addAttribute("bookList", allBooks);

        return "bookshelf";
    }

    @RequestMapping("/bookDetail")
    public String bookDetail(@RequestParam("id") Long id, Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            Optional<UserAccount> userAccount = userAccountService.findByUserName(userName);
            if (!userAccount.isPresent()) {
                return "redirect:/badRequest";
            }
            model.addAttribute("user", userAccount.get());
        }

        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return "redirect:/badRequest";
        }

        model.addAttribute("book", bookOptional.get());
        List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);

        return "bookDetail";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("classActiveForgetPassword", true);

        logger.info("Forget password for email: {}", email);

        Optional<UserAccount> userAccountOptional = userAccountService.findByEmail(email);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }
        //TODO: token and email

        return "myAccount";
    }

    @PostMapping("/newUser")
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String email, @ModelAttribute("username") String username, Model model) {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

        logger.info("Creating new user. email: {}, userName: {}", email, username);

        Optional<UserAccount> byUserNameOptional = userAccountService.findByUserName(username);
        if (byUserNameOptional.isPresent()) {
            model.addAttribute("usernameExists", true);
            return "myAccount";
        }

        Optional<UserAccount> byEmailOptional = userAccountService.findByEmail(email);
        if (byEmailOptional.isPresent()) {
            model.addAttribute("emailExists", true);
            return "myAccount";
        }

        String password = PasswordUtils.randomPassword();
        String encryptedPassword = PasswordUtils.getEncoder().encode(password);

        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(username);
        userAccount.setEmail(email);
        userAccount.setPassword(encryptedPassword);

        Optional<Role> roleOptional = roleService.findByName(ROLE_USER);
        if (!roleOptional.isPresent()) {
            model.addAttribute("message", NO_ROLE_PRESENT_MESSAGE);
            return "redirect:/badRequest";
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(roleOptional.get(), userAccount));
        userAccount.setUserRoles(userRoles);

        userAccountService.createUser(userAccount, userRoles);
        // TODO: send email

        model.addAttribute("emailSent", "true");

        return "myAccount";
    }

    @RequestMapping("/newUser")
    public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
        Optional<PasswordResetToken> passwordResetTokenOptional = userAccountService.getPasswordResetToken(token);

        if (!passwordResetTokenOptional.isPresent()) {
            model.addAttribute("message", INVALID_TOKEN_MESSAGE);
            return "redirect:/badRequest";
        }
        PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
        UserAccount userAccount = passwordResetToken.getUserAccount();
        String userName = userAccount.getUserName();

        UserDetails userDetails = jdbcSecurityService.loadUserByUsername(userName);

        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAccount, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

}
