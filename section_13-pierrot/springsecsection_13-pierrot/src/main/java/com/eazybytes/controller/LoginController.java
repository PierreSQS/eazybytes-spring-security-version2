package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LoginController {

    private final CustomerRepository customerRepository;

    public LoginController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        String authenticationName = authentication.getName();
        List<Customer> customers = customerRepository.findByEmail(authenticationName);
        if (!CollectionUtils.isEmpty(customers)) {
            return customers.get(0);
        } else {
            log.info("user with email: {} not found:",authenticationName);
            return null;
        }

    }

}
