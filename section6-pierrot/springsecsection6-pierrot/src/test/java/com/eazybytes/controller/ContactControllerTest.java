package com.eazybytes.controller;

import com.eazybytes.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ContactRepository mockContactRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveContactInquiryDetails() {
    }
}