package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.entities.Book;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.services.BookService;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 8/11/2018.
 */
@Controller
public class BookController {

    private BookService bookService;
    private UserAccountService userAccountService;

    public BookController(BookService bookService,
                          UserAccountService userAccountService) {
        this.bookService = bookService;
        this.userAccountService = userAccountService;
    }

    @RequestMapping("/bookShelf")
    public String bookShelf(Model model) {
        List<Book> allBooks = bookService.findAll();

        model.addAttribute("bookList", allBooks);

        return "bookShelf";
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

}
