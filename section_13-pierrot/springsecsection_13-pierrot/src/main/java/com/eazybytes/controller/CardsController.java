package com.eazybytes.controller;

import com.eazybytes.model.Cards;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.CardsRepository;
import com.eazybytes.repository.CustomerRepository;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CardsController {

    private final CardsRepository cardsRepository;

    private final CustomerRepository customerRepository;

    public CardsController(CardsRepository cardsRepository, CustomerRepository customerRepository) {
        this.cardsRepository = cardsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {

        List<Customer> custByEmail = customerRepository.findByEmail(email);
        if (!CollectionUtils.isEmpty(custByEmail)) {
            return cardsRepository.findByCustomerId(custByEmail.get(0).getId());
        }
        log.info("user with email: {} not found:",email);
        return Collections.emptyList();
    }

}
