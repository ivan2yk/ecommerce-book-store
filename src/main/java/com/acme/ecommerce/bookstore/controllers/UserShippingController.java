package com.acme.ecommerce.bookstore.controllers;

import com.acme.ecommerce.bookstore.dtos.UserShippingDTO;
import com.acme.ecommerce.bookstore.dtos.mapper.UserShippingMapper;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.UserShipping;
import com.acme.ecommerce.bookstore.services.UserAccountService;
import com.acme.ecommerce.bookstore.services.UserShippingService;
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
public class UserShippingController {

    private UserAccountService userAccountService;
    private UserShippingService userShippingService;
    private UserShippingMapper userShippingMapper;

    private static final String INVALID_USER_MESSAGE = "Invalid user";
    private static final String INVALID_USER_SHIPPING_MESSAGE = "Shipping address does not exists";

    public UserShippingController(UserAccountService userAccountService,
                                  UserShippingService userShippingService,
                                  UserShippingMapper userShippingMapper) {
        this.userAccountService = userAccountService;
        this.userShippingService = userShippingService;
        this.userShippingMapper = userShippingMapper;
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

    @RequestMapping("/updateUserShipping")
    public String updateUserShipping(@ModelAttribute("id") Long shippingAddressId, Principal principal, Model model) {
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
    public String setDefaultShipping(@ModelAttribute("defaultShippingAddressId") Long idDefaultShippingAddressId, Principal principal, Model model) {
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
    public String removeShipping(@ModelAttribute("id") Long userShippingId, Principal principal, Model model) {
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

    @PostMapping(value = "/addNewShippingAddress")
    public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShippingDTO userShippingDTO, Principal principal, Model model) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUserName(principal.getName());

        if (!userAccountOptional.isPresent()) {
            model.addAttribute("message", INVALID_USER_MESSAGE);
            return "redirect:/badRequest";
        }

        log.info("Updating user shipping: {}", userShippingDTO);

        UserAccount userAccount = userAccountOptional.get();

        UserShipping userShipping = userShippingMapper.toEntity(userShippingDTO);
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

}
