package com.eazybytes.controller;

import com.eazybytes.repository.CardsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CardsController.class)
class CardsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardsRepository mockCardsRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCardDetails() {
    }
}