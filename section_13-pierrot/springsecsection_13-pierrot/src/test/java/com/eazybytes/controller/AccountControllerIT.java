package com.eazybytes.controller;

import com.eazybytes.model.Accounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

// TODO CHECK how to test in the context of JWT-Token!!!
@Disabled("Test doesn't work since we have to submit a JWT-Token-TOCHECK how to test!")
@SpringBootTest
class AccountControllerIT {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    RestTemplate restTemplate;
    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder
                .basicAuthentication("happy@example.com","12345")
                .build();
    }

    @Test
    void getAccountDetails() {
        ResponseEntity<Accounts> accountsRespEntity =
                restTemplate.getForEntity("http://localhost:8080/myAccount?id=1", Accounts.class);

        assertThat(Objects.requireNonNull(accountsRespEntity.getBody()).getAccountNumber()).isEqualTo(2147483647L);
        assertThat(accountsRespEntity.getBody().getAccountType()).isEqualTo("Savings");
    }
}
