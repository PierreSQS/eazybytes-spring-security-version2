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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class AccountControllerIT {

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

        assertThat(accountsRespEntity.getBody().getAccountNumber()).isEqualTo(2147483647L);
        assertThat(accountsRespEntity.getBody().getAccountType()).isEqualTo("Savings");
    }
}
