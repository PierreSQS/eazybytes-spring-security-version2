package com.eazybytes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardsController.class)
class CardsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(username = "Mock User",password = "MockPWD")
    void getCardDetails() throws Exception {
        mockMvc.perform(get("/myCards"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Here are the card details from the DB")))
                .andDo(print());
    }
}