package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.entities.*;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import com.acme.ecommerce.bookstore.services.RoleService;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.services.UserPaymentService;
import com.acme.ecommerce.bookstore.services.UserShippingService;
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
 * Created by Ivan on 29/10/2018.
 */
@Slf4j
@Controller
public class HomeController {

    private UserDetailsService jdbcSecurityService;
    private UserAccountService userAccountService;
    private UserPaymentService userPaymentService;
    private UserShippingService userShippingService;
    private RoleService roleService;

    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
    private static final String INVALID_USER_MESSAGE = "Invalid user";
    private static final String INVALID_USER_PAYMENT_MESSAGE = "Payment does not exists";
    private static final String INVALID_USER_SHIPPING_MESSAGE = "Shipping address does not exists";
    private static final String NO_ROLE_PRESENT_MESSAGE = "No role defined";
    private static final String ROLE_USER = "ROLE_USER";

    public HomeController(@Qualifier("jdbcUserDetailServiceImpl") UserDetailsService userDetailsService,
                          UserAccountService userAccountService,
                          UserPaymentService userPaymentService,
                          UserShippingService userShippingService,
                          RoleService roleService) {
        this.jdbcSecurityService = userDetailsService;
        this.userAccountService = userAccountService;
        this.userPaymentService = userPaymentService;
        this.userShippingService = userShippingService;
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

    @PostMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("classActiveForgetPassword", true);

        log.info("Forget password for email: {}", email);

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
        // TODO: send email

        model.addAttribute("emailSent", "true");

        return "myAccount";
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

    @RequestMapping("/listOfCreditCards")
    public String listOfCreditsCards(Model model, Principal principal) {
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
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model, Principal principal) {
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
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();
        model.addAttribute("user", userAccount);

        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        UserPayment userPayment = new UserPayment();
        UserBilling userBilling = new UserBilling();

        model.addAttribute("userPayment", userPayment);
        model.addAttribute("userBilling", userBilling);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);

        model.addAttribute("stateList", usStatesCodes);
        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShipping", userAccount.getUserShippings());

        return "myProfile";
    }

    @PostMapping("/addNewCreditCard")
    public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
                                   @ModelAttribute("userBilling") UserBilling userBilling,
                                   Principal principal,
                                   Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();
        userAccountService.updateUserBilling(userBilling, userPayment, userAccount);

        model.addAttribute("user", userAccount);
        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/updateCreditCard")
    public String updateCreditCard(@ModelAttribute("id") Long idPaymentCreditCard, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        log.info("Updating credit card: {}", idPaymentCreditCard);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();
        Optional<UserPayment> userPaymentOptional = userPaymentService.findById(idPaymentCreditCard);

        if (!userPaymentOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_PAYMENT_MESSAGE);
            return "redirect:/badRequest";
        }
        UserPayment userPayment = userPaymentOptional.get();

        if (!userAccount.getId().equals(userPayment.getUserAccount().getId())) {
            return "redirect:/badRequest";
        }

        model.addAttribute("user", userAccount);
        UserBilling userBilling = userPayment.getUserBilling();
        model.addAttribute("userPayment", userPayment);
        model.addAttribute("userBilling", userBilling);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);

        model.addAttribute("stateList", usStatesCodes);
        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @RequestMapping("/updateUserShipping")
    public String updateUserShipping(@ModelAttribute("id") Long shippingAddressId,
                                     Principal principal,
                                     Model model) {
        log.info("Showing updateUserShipping view: {}", shippingAddressId);

        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequestPage";
        }
        UserAccount userAccount = userAccountOptional.get();
        Optional<UserShipping> shippingOptional = userShippingService.findById(shippingAddressId);

        if (!shippingOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_SHIPPING_MESSAGE);
            return "redirect:/badRequest";
        }
        UserShipping userShipping = shippingOptional.get();

        model.addAttribute("user", userAccount);
        model.addAttribute("userShipping", userShipping);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);

        model.addAttribute("stateList", usStatesCodes);
        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @PostMapping("/setDefaultShippingAddress")
    public String setDefaultShipping(@ModelAttribute("defaultShippingAddressId") Long idDefaultShippingAddressId,
                                     Principal principal,
                                     Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        log.info("Setting default shipping address: {}", idDefaultShippingAddressId);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequestPage";
        }
        UserAccount userAccount = userAccountOptional.get();

        userAccountService.setUserDefaultShippingAddress(idDefaultShippingAddressId, userAccount);

        model.addAttribute("user", userAccount);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @RequestMapping("/removeUserShipping")
    public String removeShipping(@ModelAttribute("id") Long userShippingId,
                                 Principal principal,
                                 Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        log.info("Removing shipping address: {}", userShippingId);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }
        UserAccount userAccount = userAccountOptional.get();
        Optional<UserShipping> userShippingOptional = userShippingService.findById(userShippingId);

        if (!userShippingOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_SHIPPING_MESSAGE);
            return "redirect:/badRequest";
        }

        userShippingService.deleteById(userShippingId);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);
        model.addAttribute("stateList", usStatesCodes);

        model.addAttribute("user", userAccount);
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @RequestMapping("/removeCreditCard")
    public String removeCreditCard(@ModelAttribute("id") Long idPaymentCreditCard, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        log.info("Updating credit card: {}", idPaymentCreditCard);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }
        UserAccount userAccount = userAccountOptional.get();
        Optional<UserPayment> userPaymentOptional = userPaymentService.findById(idPaymentCreditCard);

        if (!userPaymentOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_PAYMENT_MESSAGE);
            return "redirect:/badRequest";
        }
        UserPayment userPayment = userPaymentOptional.get();

        if (!userAccount.getId().equals(userPayment.getUserAccount().getId())) {
            return "redirect:/badRequest";
        }
        userPaymentService.deleteById(idPaymentCreditCard);

        model.addAttribute("user", userAccount);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @PostMapping("/setDefaultPayment")
    public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long idDefaultPaymentId, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        log.info("Setting default credit card: {}", idDefaultPaymentId);

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }
        UserAccount userAccount = userAccountOptional.get();

        log.info("Setting default payment: {}, {}", idDefaultPaymentId, userAccount);

        userAccountService.setUserDefaultPayment(idDefaultPaymentId, userAccount);

        model.addAttribute("user", userAccount);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

        return "myProfile";
    }

    @PostMapping(value = "/addNewShippingAddress")
    public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        log.info("Updating user shipping: {}", userShipping);

        UserAccount userAccount = userAccountOptional.get();
        userAccountService.updateUserShipping(userShipping, userAccount);

        model.addAttribute("user", userAccount);
        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        return "myProfile";
    }

    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model, Principal principal) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();
        model.addAttribute("user", userAccount);
        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        UserShipping userShipping = new UserShipping();

        model.addAttribute("userShipping", userShipping);

        List<String> usStatesCodes = USConstants.US_STATES_CODES;
        Collections.sort(usStatesCodes);

        model.addAttribute("stateList", usStatesCodes);
        model.addAttribute("userPaymentList", userAccount.getUserPayments());
        model.addAttribute("userShippingList", userAccount.getUserShippings());

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

}
