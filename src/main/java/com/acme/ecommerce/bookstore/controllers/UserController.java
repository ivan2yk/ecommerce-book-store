package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.entities.Role;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserRole;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.services.EmailBuilderService;
import com.acme.ecommerce.bookstore.services.RoleService;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.utils.PasswordUtils;
import com.acme.ecommerce.bookstore.utils.USConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * Created by Ivan on 30/01/2019.
 */
@Controller
@Slf4j
public class UserController {

    private UserDetailsService jdbcSecurityService;
    private UserAccountService userAccountService;
    private RoleService roleService;
    private EmailBuilderService emailBuilderService;

    private static final String INVALID_USER_MESSAGE = "Invalid user";
    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
    private static final String NO_ROLE_PRESENT_MESSAGE = "No role defined";
    private static final String ROLE_USER = "ROLE_USER";

    public UserController(@Qualifier("jdbcUserDetailServiceImpl") UserDetailsService jdbcSecurityService,
                          UserAccountService userAccountService,
                          RoleService roleService,
                          EmailBuilderService emailBuilderService) {
        this.jdbcSecurityService = jdbcSecurityService;
        this.userAccountService = userAccountService;
        this.roleService = roleService;
        this.emailBuilderService = emailBuilderService;
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();

        model.addAttribute("user", userAccount);
        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);
        model.addAttribute("stateList", usStatesCodes);

        model.addAttribute("classActiveEdite", true);

        return "myProfile";
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

    @PostMapping("/newUser")
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String email, @ModelAttribute("username") String username, Model model) {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

        log.info("Creating new user. email: {}, userName: {}", email, username);

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

        String token = UUID.randomUUID().toString();
        userAccountService.createPasswordResetTokenForUser(userAccount, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        emailBuilderService.constructResetTokenEmail(userAccount, password, appUrl, token);

        model.addAttribute("emailSent", "true");

        return "myAccount";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {
        model.addAttribute("classActiveForgetPassword", true);

        log.info("Forget password for email: {}", email);

        Optional<UserAccount> userAccountOptional = userAccountService.findByEmail(email);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }

        UserAccount userAccount = userAccountOptional.get();
        String randomPassword = PasswordUtils.randomPassword();
        String randomEncodedPassword = PasswordUtils.getEncoder().encode(randomPassword);

        userAccount.setPassword(randomEncodedPassword);
        userAccountService.save(userAccount);

        String token = UUID.randomUUID().toString();
        userAccountService.createPasswordResetTokenForUser(userAccount, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        emailBuilderService.constructResetPasswordTokenEmail(userAccount, randomEncodedPassword, appUrl, token);

        model.addAttribute("forgetPasswordEmailSent", "true");

        return "myAccount";
    }

}
