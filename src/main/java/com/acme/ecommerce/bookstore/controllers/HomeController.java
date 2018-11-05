package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.services.RoleService;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.utils.PasswordUtils;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Ivan on 29/10/2018.
 */
@Controller
public class HomeController {

    private UserDetailsService jdbcSecurityService;
    private UserAccountService userAccountService;
    private RoleService roleService;

    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
    private static final String NO_ROLE_PRESENT_MESSAGE = "No role defined";
    private static final String ROLE_USER = "ROLE_USER";

    public HomeController(@Qualifier("jdbcUserDetailServiceImpl") UserDetailsService userDetailsService,
                          UserAccountService userAccountService,
                          RoleService roleService) {
        this.jdbcSecurityService = userDetailsService;
        this.userAccountService = userAccountService;
        this.roleService = roleService;
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

    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model) {
        model.addAttribute("classActiveForgetPassword", true);
        return "myAccount";
    }

    @PostMapping("/newUser")
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String email, @ModelAttribute("username") String username, Model model) {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

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
