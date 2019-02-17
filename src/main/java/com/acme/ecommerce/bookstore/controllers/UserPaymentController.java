package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.dtos.UserBillingDTO;
import com.acme.ecommerce.bookstore.dtos.UserPaymentDTO;
import com.acme.ecommerce.bookstore.dtos.mapper.UserBillingMapper;
import com.acme.ecommerce.bookstore.dtos.mapper.UserPaymentMapper;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import com.acme.ecommerce.bookstore.entities.UserPayment;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.services.UserPaymentService;
import com.acme.ecommerce.bookstore.utils.USConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ivan on 30/01/2019.
 */
@Controller
@Slf4j
public class UserPaymentController {

    private UserAccountService userAccountService;
    private UserPaymentService userPaymentService;
    private UserPaymentMapper userPaymentMapper;
    private UserBillingMapper userBillingMapper;

    private static final String INVALID_USER_MESSAGE = "Invalid user";
    private static final String INVALID_USER_PAYMENT_MESSAGE = "Payment does not exists";

    public UserPaymentController(UserAccountService userAccountService,
                                 UserPaymentService userPaymentService,
                                 UserPaymentMapper userPaymentMapper,
                                 UserBillingMapper userBillingMapper) {
        this.userAccountService = userAccountService;
        this.userPaymentService = userPaymentService;
        this.userPaymentMapper = userPaymentMapper;
        this.userBillingMapper = userBillingMapper;
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
    public String addNewCreditCard(@ModelAttribute("userPayment") UserPaymentDTO userPaymentDTO, @ModelAttribute("userBilling") UserBillingDTO userBillingDTO, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        UserAccount userAccount = userAccountOptional.get();

        UserBilling userBilling = this.userBillingMapper.toEntity(userBillingDTO);
        UserPayment userPayment = this.userPaymentMapper.toEntity(userPaymentDTO);

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

}
